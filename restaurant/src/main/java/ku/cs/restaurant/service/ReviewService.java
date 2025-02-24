package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.Review;
import ku.cs.restaurant.repository.OrderRepository;
import ku.cs.restaurant.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public Review submitReview(UUID orderId, int rating, String comment) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Review review = new Review();
        review.setOrder(order);
        review.setCustomer(order.getUser());
        review.setRating(rating);
        review.setComment(comment);
        review.setLikeCount(0);
        return reviewRepository.save(review);
    }

    public void like(UUID id, boolean isLiked) {
        try {
            Optional<Review> reviewOptional = reviewRepository.findById(id);

            Review review = reviewOptional.orElseThrow(() ->
                    new NoSuchElementException("Review not found with id: " + id)
            );

            if (isLiked) {
                review.setLikeCount(review.getLikeCount() + 1);
            } else {
                review.setLikeCount(Math.max(0, review.getLikeCount() - 1));
            }

            reviewRepository.save(review);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Review not found with id: " + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}