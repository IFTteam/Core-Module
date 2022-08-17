package springredis.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springredis.demo.entity.Audience;

public interface AudienceRepository extends JpaRepository<Audience, Long> {

}
