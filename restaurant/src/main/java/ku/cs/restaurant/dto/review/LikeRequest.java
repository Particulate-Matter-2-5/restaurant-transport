package ku.cs.restaurant.dto.review;

import lombok.Data;

import java.util.UUID;

@Data
public class LikeRequest {
    private UUID id;
    private String isLiked;
}
