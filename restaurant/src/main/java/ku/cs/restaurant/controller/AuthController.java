package ku.cs.restaurant.controller;

import jakarta.validation.Valid;
import ku.cs.restaurant.dto.user.SigninRequest;
import ku.cs.restaurant.dto.user.SigninResponse;
import ku.cs.restaurant.dto.user.SignupRequest;
import ku.cs.restaurant.dto.user.SignupResponse;
import ku.cs.restaurant.exception.UserRegistrationException;
import ku.cs.restaurant.service.AuthService;
import ku.cs.restaurant.service.UserService;
import ku.cs.restaurant.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            ApiResponse<SignupResponse> signupResponse = userService.createUser(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
        } catch (UserRegistrationException e) {
            return handleUserRegistrationException(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ApiResponse<SignupResponse>> handleUserRegistrationException(UserRegistrationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<ApiResponse<SigninResponse>> signin(@RequestBody SigninRequest signinRequest) {
        try {
            ApiResponse<SigninResponse> signinResponse = authService.signIn(signinRequest);
            return ResponseEntity.ok(signinResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Sign-in failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<?>> validateToken(@RequestBody String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Token must not be empty.", null));
        }

        try {
            boolean isAuth = authService.validateToken(token);

            if (isAuth) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Your JWT is valid.", null));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Your JWT is invalid.", null));
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An error occurred while validating the token.", null));
        }
    }
}
