package ku.cs.restaurant.dto.order;

import ku.cs.restaurant.entity.Food;
import lombok.Data;

@Data
public class FoodOrder {
    private Food food;
    private int quantity;
}
