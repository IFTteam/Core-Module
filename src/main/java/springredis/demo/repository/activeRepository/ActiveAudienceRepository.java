package springredis.demo.repository.activeRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import springredis.demo.entity.Audience;
import springredis.demo.entity.activeEntity.ActiveAudience;

public interface ActiveAudienceRepository extends JpaRepository<ActiveAudience, Long> {
}
