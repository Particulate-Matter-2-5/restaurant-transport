package ku.cs.restaurant.dto.Receipt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRequest {
    @NotBlank
    private double total;
}
