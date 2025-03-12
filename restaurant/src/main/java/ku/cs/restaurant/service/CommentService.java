package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.CommentDTO;
import ku.cs.restaurant.entity.Comment;
import ku.cs.restaurant.entity.CommentKey;
import ku.cs.restaurant.entity.Review;
import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.repository.CommentRepository;
import ku.cs.restaurant.repository.ReviewRepository;
import ku.cs.restaurant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getComment(UUID id) {
        return commentRepository.findById(id);
    }

    public Comment addComment(CommentDTO commentDTO) {
        UUID reviewId = commentDTO.getReviewId();
        UUID userId = commentDTO.getUserId();

        // Fetch review and user from DB
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create CommentKey
        CommentKey commentKey = new CommentKey(reviewId, userId);

        // Construct Comment object
        Comment comment = new Comment();
        comment.setId(commentKey);
        comment.setComment(commentDTO.getComment());
        comment.setReview(review);
        comment.setUser(user);

        return commentRepository.save(comment);
    }
}
