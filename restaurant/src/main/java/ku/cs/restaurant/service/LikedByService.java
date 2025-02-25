package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.LikedBy;
import ku.cs.restaurant.entity.LikedByKey;
import ku.cs.restaurant.entity.Review;
import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.repository.LikedByRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LikedByService {
    private final LikedByRepository likedByRepository;

    public void createLikedBy(Review review, User user) {
        LikedByKey key = new LikedByKey(review.getId(), user.getId());

        LikedBy likedBy = new LikedBy();
        likedBy.setId(key);
        likedBy.setUser(user);
        likedBy.setReview(review);

        likedByRepository.save(likedBy);
    }

    public void deleteLikedBy(Review review, User user) {
        LikedByKey key = new LikedByKey(review.getId(), user.getId());

        if (likedByRepository.existsById(key)) {
            likedByRepository.deleteById(key);
        }
    }

    public boolean isLikedBy(UUID reviewId, UUID userId) {
        LikedByKey key = new LikedByKey(reviewId, userId);
        return likedByRepository.existsById(key);
    }
}
