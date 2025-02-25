package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.LikedBy;
import ku.cs.restaurant.entity.LikedByKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedByRepository extends JpaRepository<LikedBy, LikedByKey> {
}
