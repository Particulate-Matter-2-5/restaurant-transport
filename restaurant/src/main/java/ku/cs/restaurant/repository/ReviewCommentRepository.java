package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Comment;
import ku.cs.restaurant.entity.ReviewComment;
import ku.cs.restaurant.entity.ReviewCommentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, ReviewCommentKey> {
    List<ReviewComment> findReviewCommentByReviewId(UUID reviewId);
    @Query("""
    SELECT r.comment FROM ReviewComment r WHERE r.key.reviewId = :reviewId
    """)
    List<Comment> findCommentByReviewId(UUID reviewId);
}
