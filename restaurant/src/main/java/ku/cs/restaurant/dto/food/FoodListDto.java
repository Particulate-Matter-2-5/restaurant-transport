package ku.cs.restaurant.dto.food;
import ku.cs.restaurant.entity.Food;
import lombok.Data;

import java.util.List;
@Data
public class FoodListDto {
    private List<FoodDto> foods;
}
