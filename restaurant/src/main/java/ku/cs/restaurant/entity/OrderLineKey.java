package ku.cs.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderLineKey implements Serializable {
    @Column(name = "f_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID foodId;

    @Column(name = "o_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;
}
