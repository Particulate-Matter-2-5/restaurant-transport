package ku.cs.restaurant.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import ku.cs.restaurant.entity.RecipeKey;
import lombok.Data;

@Data
public class UpdateQtyRequest {
    @NotBlank
    private RecipeKey id;
    @NotBlank
    private int qty;
}
