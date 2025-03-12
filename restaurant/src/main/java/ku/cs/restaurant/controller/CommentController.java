package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.dto.CommentDTO;
import ku.cs.restaurant.entity.Comment;
import ku.cs.restaurant.service.CommentService;
import ku.cs.restaurant.service.ReviewCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ReviewCommentService reviewCommentService;

    @GetMapping("comment")
    public ResponseEntity<ApiResponse<List<Comment>>> getComments() {
        try {
            List<Comment> comments = commentService.getComments();
            return ResponseEntity.ok(new ApiResponse<>(true, "Comments retrieved successfully.", comments));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("comment/{reviewId}")
    public ResponseEntity<ApiResponse<List<Comment>>> getComment(@PathVariable UUID reviewId) {
        try {
            List<Comment> commentsOfReviewId = reviewCommentService.findCommentByReviewId(reviewId);
            return ResponseEntity.ok(new ApiResponse<>(true, "get comments by review id success", commentsOfReviewId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PostMapping("comment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        try {
            Comment savedComment = commentService.addComment(commentDTO);
            return ResponseEntity.ok(savedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

}
