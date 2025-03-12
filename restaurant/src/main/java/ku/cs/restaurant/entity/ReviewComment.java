package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewComment {
    @EmbeddedId
    private ReviewCommentKey key;

    @ManyToOne
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @MapsId("commentId")
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
