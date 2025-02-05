package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderLine {
    @EmbeddedId
    private OrderLineKey id;

    @Column(name = "o_qty")
    private int qty;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "o_id")
    private Order order;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "f_id")
    private Food food;
}
