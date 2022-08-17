package springredis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springredis.demo.entity.Campaign;
import springredis.demo.entity.Node;
import springredis.demo.entity.User;
import springredis.demo.repository.*;

import java.sql.Date;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private AudienceRepository audienceRepository;
    @Autowired
    private CampaignRepository campaignRepository;


    //这里我随便测了几个应该都是可以用的，注意有些参数是必须要有的
    @GetMapping("/test/getAllNode")
    public List<Node> getAllNode(){
        return nodeRepository.findAll();
    }

    @PostMapping("/test/addNode")
    public Node addNode(Node node){

        return nodeRepository.save(node);
    }

    @GetMapping("/test/getAllUser")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //这里展示了一下如果想用与变量名不同的Key的传参方法，就要列出所有的属性，很是麻烦，建议直接使用下面那种传参
//    @PostMapping("/test/addUser")
//    public User addUser(
//            @RequestParam("domain") String domain,
//            @RequestParam("avatarUrl") String avatarUrl,
//            @RequestParam("companyId") String companyId,
//            @RequestParam("unsubscribeLink") String unsubscribeLink,
//            @RequestParam("subscriptionType") String subscriptionType,
//
//            @RequestParam("lastModifiedBy") String lastModifiedBy,
//            @RequestParam("contactName") String contactName,
//            @RequestParam("contactEmail") String contactEmail,
//            @RequestParam("contactPhone") String contactPhone,
//            @RequestParam("address") String address,
//            @RequestParam("api_id") Long api_id,
//            @RequestParam("preferEmailSvcProvider") Integer preferEmailSvcProvider,
//            @RequestParam("onlySendDeliverableEmail") Integer onlySendDeliverableEmail,
//            @RequestParam("unsubscribeType") Integer unsubscribeType,
//            @RequestParam("salesforceApiKey") String salesforceApiKey,
//            @RequestParam("hubspotApiKey") String hubspotApiKey,
//            @RequestParam("shopifyApiKey") String shopifyApiKey,
//            @RequestParam("facebookAdsApiKey") String facebookAdsApiKey){
//
//        User user = new User(domain,avatarUrl,domain,companyId,unsubscribeLink,subscriptionType,null,lastModifiedBy,null,contactName,contactEmail,contactPhone,address,api_id,preferEmailSvcProvider,onlySendDeliverableEmail,unsubscribeType,salesforceApiKey,hubspotApiKey,shopifyApiKey,facebookAdsApiKey);
//        return userRepository.save(user);
//    }

    @PostMapping("/test/addUser")
    public User addUser(User user){
        return userRepository.save(user);
    }
    //TODO: your own api
}