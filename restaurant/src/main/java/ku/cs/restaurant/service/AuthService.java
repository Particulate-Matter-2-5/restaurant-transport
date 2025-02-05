package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.dto.user.SigninRequest;
import ku.cs.restaurant.dto.user.SigninResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ApiResponse<SigninResponse> signIn(SigninRequest signinRequest);
    Boolean validateToken(String token);
}