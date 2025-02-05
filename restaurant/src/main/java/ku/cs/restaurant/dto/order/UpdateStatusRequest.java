package ku.cs.restaurant.dto.order;

import jakarta.validation.constraints.NotBlank;
import ku.cs.restaurant.entity.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateStatusRequest {
    @NotBlank
    private UUID id;
    @NotBlank
    private OrderStatus status;
}
