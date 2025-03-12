package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.dto.CommentDTO;
import ku.cs.restaurant.entity.Comment;
import ku.cs.restaurant.entity.CommentKey;
import ku.cs.restaurant.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("comment")
    public ResponseEntity<ApiResponse<List<Comment>>> getComments() {
        try {
            List<Comment> comments = commentService.getComments();
            return ResponseEntity.ok(new ApiResponse<>(true, "Comments retrieved successfully.", comments));
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
