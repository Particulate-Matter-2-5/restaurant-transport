package ku.cs.restaurant.dto.recipe;

import lombok.Data;

import java.util.List;

@Data
public class IngredientQtyRequest {
    private List<MappedIngredientId> ingredientMap;
}
