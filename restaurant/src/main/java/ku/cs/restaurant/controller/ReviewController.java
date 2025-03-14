package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.dto.review.IsLikedByRequest;
import ku.cs.restaurant.dto.review.IsLikedByResponse;
import ku.cs.restaurant.dto.review.LikeRequest;
import ku.cs.restaurant.dto.review.ReviewRequest;
import ku.cs.restaurant.entity.RecipeKey;
import ku.cs.restaurant.entity.Review;
import ku.cs.restaurant.service.LikedByService;
import ku.cs.restaurant.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final LikedByService likedByService;

    @PostMapping("/review/submit")
    public ResponseEntity<Review> submitReview(@RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.submitReview(
                reviewRequest.getOrderId(),
                reviewRequest.getRating(),
                reviewRequest.getComment()
        );
        return ResponseEntity.ok(review);
    }

    @PostMapping("/review/like")
    public ResponseEntity<Review> likeReview(@RequestBody LikeRequest likeRequest) {
        try {
            UUID reviewId = likeRequest.getReviewId();
            UUID userId = likeRequest.getUserId();
            String isLiked = likeRequest.getIsLiked();

            reviewService.like(reviewId, userId, isLiked.equals("true"));

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/review")
    public ResponseEntity<ApiResponse<List<Review>>> getReviews() {
        try {
            List<Review> reviewList = reviewService.getReviews();
            return ResponseEntity.ok(new ApiResponse<>(true, "Review List fetched successfully.", reviewList));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PostMapping("/review/isLiked")
    public ResponseEntity<ApiResponse<IsLikedByResponse>> getIsLiked(@RequestBody IsLikedByRequest req) {
        try {
            boolean isLiked = likedByService.isLikedBy(req.getReviewId(), req.getUserId());
            IsLikedByResponse isLikedByResponse = new IsLikedByResponse();
            isLikedByResponse.setLiked(isLiked);
            return ResponseEntity.ok(new ApiResponse<>(true, "success", isLikedByResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable UUID id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Review deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Review not found.", null));
        }
    }

}