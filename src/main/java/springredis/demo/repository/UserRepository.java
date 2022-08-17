package springredis.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springredis.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
