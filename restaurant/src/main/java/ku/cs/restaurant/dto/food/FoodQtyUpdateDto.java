package ku.cs.restaurant.dto.food;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class FoodQtyUpdateDto {
    @NotBlank
    private UUID id;
    @NotBlank
    private int qty;
}
