package springredis.demo.repository.activeRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springredis.demo.entity.Audience;
import springredis.demo.entity.Node;
import springredis.demo.entity.activeEntity.ActiveNode;

import java.util.List;

public interface ActiveNodeRepository extends JpaRepository<ActiveNode, Long> {
    @Query(value = "select * from active_node t where t.node_id = node_id",nativeQuery = true)
    ActiveNode findByDBNodeId(@Param("node_id") Long NodeId);

}
