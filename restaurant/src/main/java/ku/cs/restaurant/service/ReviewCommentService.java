package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Comment;
import ku.cs.restaurant.repository.ReviewCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewCommentService {
    private final ReviewCommentRepository reviewCommentRepository;

    public List<Comment> findCommentByReviewId(UUID reviewId) {
        try {
            return reviewCommentRepository.findCommentByReviewId(reviewId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
