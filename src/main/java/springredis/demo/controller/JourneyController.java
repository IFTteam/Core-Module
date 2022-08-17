package springredis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springredis.demo.entity.CoreModuleTask;
import springredis.demo.entity.Journey;
import springredis.demo.entity.Node;
import springredis.demo.entity.activeEntity.ActiveJourney;
import springredis.demo.entity.activeEntity.ActiveNode;
import springredis.demo.repository.JourneyRepository;
import springredis.demo.repository.NodeRepository;
import springredis.demo.repository.activeRepository.ActiveJourneyRepository;
import springredis.demo.repository.activeRepository.ActiveNodeRepository;
import springredis.demo.serializer.SeDeFunction;
import springredis.demo.tasks.TaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class JourneyController {
    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ActiveJourneyRepository activeJourneyRepository;


    @Autowired
    private ActiveNodeRepository activeNodeRepository;
    @PostMapping("/journey/saveJourney")//保存Journey,仅仅保存Serialized部分
    public Journey saveJourney(Journey journey){

        return journeyRepository.save(journey);
    }

    @PostMapping("/journey/activateJourney")//激活Journey,查取数据库，反序列化
    public Journey activateJourney(Journey journey){


        Optional<Journey> opsJ = journeyRepository.findById(journey.getId());
        if (opsJ.isPresent()){
            journey = JourneyParse(opsJ.get());
            journeyRepository.save(journey);


        }
        return journey;
    }

    private Journey JourneyParse(Journey journey) {
        //Deserialize function
        SeDeFunction seDeFunction = new SeDeFunction();
        List<Node> deserializedJourney =  seDeFunction.deserializing(journey.getJourneySerialized());

        //Setup active journey
        ActiveJourney activeJourney = new ActiveJourney();
        activeJourney.setJourneyId(journey.getId());
        activeJourneyRepository.save(activeJourney);


        //Initialize Journey function
        int n = deserializedJourney.size();
        //1.Use map frontEndId->BackEndId and replace the node nexts frontEndId->BackEndId
        HashMap<Long,Long> keyHash = new HashMap<>();
        List<Node> heads = new ArrayList<>();
        for (int i=0; i<n; i++){
            deserializedJourney.set(i,nodeRepository.save(deserializedJourney.get(i)));
            keyHash.put(deserializedJourney.get(i).getFrontEndId(),deserializedJourney.get(i).getId());
            //set up active node
            ActiveNode activeNode = new ActiveNode();
            activeNode.setActiveJourney(activeJourney);
            activeNode.setNodeId(deserializedJourney.get(i).getId());
            activeNodeRepository.save(activeNode);
        }

        // replace nexts ID
        for (int i=0; i<n; i++){
            Node nodeI = deserializedJourney.get(i);
            List<Long> nexts = nodeI.getNexts();
            for (int j=0; j<nexts.size(); j++){
                nexts.set(j, keyHash.get(nexts.get(j)));
            }
            nodeI.setNexts(nexts);
            nodeRepository.save(deserializedJourney.get(i));

            if (nodeI.getHeadOrTail()==1){//add to start list if the node is a start node
                heads.add(nodeI);
            }
        }
        //2.Start Journey from start node
        for(int i=0; i<heads.size();i++){
            CoreModuleTask coreModuleTask = new CoreModuleTask();
            coreModuleTask.setNodeId(heads.get(i).getId());
            coreModuleTask.setTargetNodeId(activeNodeRepository.findByDBNodeId(heads.get(i).getId()).getId());
            TaskExecutor taskExecutor = new TaskExecutor(coreModuleTask);
            taskExecutor.callModule(heads.get(i));
        }
        return journey;
    }


}
