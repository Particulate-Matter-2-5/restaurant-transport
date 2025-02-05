package ku.cs.restaurant.dto.food;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class FoodDeleteDto {
    @NotBlank
    private UUID id;
}
