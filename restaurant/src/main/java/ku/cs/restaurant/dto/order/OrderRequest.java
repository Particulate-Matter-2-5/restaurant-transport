package ku.cs.restaurant.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<FoodOrder> foods;

    public double calculateTotal() {
        double total = 0;
        for (FoodOrder foodOrder : foods) {
            total += foodOrder.getQuantity() * foodOrder.getFood().getPrice();
        }
        return total; // คืนค่าผลรวม
    }

    public int sumQuantity() {
        int sum = 0;
        for (FoodOrder foodOrder : foods) {
            sum += foodOrder.getQuantity();
        }
        return sum;
    }
}