package ku.cs.restaurant.dto.food;

import ku.cs.restaurant.entity.Food;
import lombok.Data;

@Data
public class FoodDto {
    private Food food;
    private int qty;
}
