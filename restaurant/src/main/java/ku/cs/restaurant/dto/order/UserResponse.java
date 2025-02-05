package ku.cs.restaurant.dto.order;

import lombok.Data;

import java.util.UUID;
@Data
public class UserResponse {
    private UUID id;
    private String username;
    private String phone;
    private String role;
}
