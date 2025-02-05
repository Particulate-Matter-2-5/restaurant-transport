package ku.cs.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Data
@Entity
public class Financial {
    @Id
    @JdbcTypeCode(SqlTypes.LOCAL_DATE)
    private LocalDate date;

    private double income;
    private double expense;
    private double total;
}
