package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.CommentDTO;
import ku.cs.restaurant.entity.*;
import ku.cs.restaurant.repository.CommentRepository;
import ku.cs.restaurant.repository.ReviewCommentRepository;
import ku.cs.restaurant.repository.ReviewRepository;
import ku.cs.restaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public Comment addComment(CommentDTO commentDTO) {
        UUID reviewId = commentDTO.getReviewId();
        UUID userId = commentDTO.getUserId();

        // Fetch review and user from DB
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Construct Comment object
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setUser(user);
        comment.setReview(review);

        Comment savedComment = commentRepository.save(comment);

        // map to reviewComment tables
        ReviewCommentKey key = new ReviewCommentKey(reviewId, savedComment.getCommentId());
        ReviewComment reviewComment = new ReviewComment();
        reviewComment.setComment(comment);
        reviewComment.setReview(review);
        reviewComment.setKey(key);

        reviewCommentRepository.save(reviewComment);

        return comment;
    }
}
