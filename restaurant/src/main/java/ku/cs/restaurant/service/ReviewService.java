package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.Review;
import ku.cs.restaurant.entity.User;
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
    // services
    private final LikedByService likedByService;
    private final UserService userService;
    // repository
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

    /**
     * เพิ่ม like_count และ บันทึกว่ารีวิวถูกไลค์โดยใคร
     * @param review_id     id ของ review ที่ถูกไลค์หรืออันไลค์
     * @param user_id       id ของ user ที่กดไลค์หรืออันไลค์
     * @param isLiked       กดไลค์หรืออันไลค์ true, false
     */
    public void like(UUID review_id, UUID user_id, boolean isLiked) {
        try {
            Optional<Review> reviewOptional = reviewRepository.findById(review_id);

            // หา object review เพื่อเอามาแก้ไข like_count
            Review review = reviewOptional.orElseThrow(() ->
                    new NoSuchElementException("Review not found with id: " + review_id)
            );
            // หา object user จาก db เพื่อเอามาเพิ่มใน table liked_by
            User user = userService.getUserById(user_id).orElseThrow(() ->
                    new NoSuchElementException("User not found with id: " + user_id)
            );
            // ถ้ากดไลค์ isLiked เป็น true
            if (isLiked) {
                // เพิ่มยอดไลค์ & บันทึกว่า review ไลค์โดยใคร
                review.setLikeCount(review.getLikeCount() + 1);
                likedByService.createLikedBy(review, user);
            } else {
                // ลดยอดไลค์ & ลบบันทึก review ไลค์โดยใคร
                review.setLikeCount(Math.max(0, review.getLikeCount() - 1));
                likedByService.deleteLikedBy(review, user);
            }
            // บันทึก review object เข้า db
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}