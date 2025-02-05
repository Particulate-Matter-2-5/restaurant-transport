package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.OrderLineKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLineKey> {
    Optional<OrderLine> findById(OrderLineKey id);
}
