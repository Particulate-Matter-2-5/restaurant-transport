package ku.cs.restaurant.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CommentDTO {
    private UUID reviewId;
    private UUID userId;
    private String comment;
}