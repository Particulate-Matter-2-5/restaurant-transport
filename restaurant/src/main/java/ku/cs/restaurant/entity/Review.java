package ku.cs.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(unique = true, nullable = false, name = "review_id")
    private UUID id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "like_count")
    private int likeCount = 0;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LikedBy> likedBy;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
