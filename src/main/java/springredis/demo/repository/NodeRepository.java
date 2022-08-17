package springredis.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springredis.demo.entity.Node;

public interface NodeRepository extends JpaRepository<Node, Long> {
}
