package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.service.OrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderLineController {
    private final OrderLineService service;

    public OrderLineController(OrderLineService service) {
        this.service = service;
    }

    @GetMapping("/order_line")
    public ResponseEntity<ApiResponse<List<OrderLine>>> getAllOrderLine() {
        try {
            List<OrderLine> orderLines = service.findOrderLine();
            return ResponseEntity.ok(new ApiResponse<>(true, "Order lines fetched successfully.", orderLines));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PatchMapping("/order_line/quantity")
    public ResponseEntity<ApiResponse<OrderLine>> updateQty(@RequestBody OrderLine orderLine) {
        try {
            OrderLine updatedOrderLine = service.updateQuantity(orderLine);
            return ResponseEntity.ok(new ApiResponse<>(true, "Order line quantity updated successfully.", updatedOrderLine));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}
