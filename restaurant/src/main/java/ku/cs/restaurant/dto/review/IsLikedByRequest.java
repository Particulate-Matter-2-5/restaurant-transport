package ku.cs.restaurant.dto.review;

import lombok.Data;

import java.util.UUID;

@Data
public class IsLikedByRequest {
    private UUID reviewId;
    private UUID userId;
}
