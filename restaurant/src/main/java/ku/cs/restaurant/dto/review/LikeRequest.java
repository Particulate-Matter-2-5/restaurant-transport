package ku.cs.restaurant.dto.review;

import lombok.Data;

import java.util.UUID;

@Data
public class LikeRequest {
    private UUID reviewId;
    private UUID userId;
    private String isLiked;
}
