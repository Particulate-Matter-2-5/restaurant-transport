package ku.cs.restaurant.dto.review;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewRequest {
    private UUID orderId;
    private UUID customerId;
    private int rating;
    private String comment;
}
