package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LikedBy {
    @EmbeddedId
    private LikedByKey id;

    @ManyToOne
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}