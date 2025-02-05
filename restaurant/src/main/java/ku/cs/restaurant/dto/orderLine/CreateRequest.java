package ku.cs.restaurant.dto.orderLine;

import jakarta.validation.constraints.NotBlank;
import ku.cs.restaurant.entity.OrderLineKey;
import lombok.Data;

@Data
public class CreateRequest {
    @NotBlank
    private OrderLineKey id;

    @NotBlank
    private int qty;

}
