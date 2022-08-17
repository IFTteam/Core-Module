package springredis.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springredis.demo.entity.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
