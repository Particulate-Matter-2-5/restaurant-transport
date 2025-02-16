package ku.cs.restaurant.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthTokenFilter(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    private static final Logger tokenLogger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = parseJwt(request);

        // Allow signin/signup auth
        if (request.getRequestURI().equals("/auth/signin")
                || request.getRequestURI().equals("/auth/signup")
                || request.getRequestURI().equals("/auth")
                || request.getRequestURI().contains("/images")
                || request.getRequestURI().startsWith("/v3/api-docs")
                || request.getRequestURI().startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response); // Proceed without JWT validation
            return;
        }

        // Validate JWT token
        if (jwt == null || !jwtUtils.validateJwtToken(jwt)) {
            tokenLogger.warn("JWT is missing or invalid.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return; // Stop processing if the JWT is missing or invalid
        }

        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Extract roles from JWT claims
            List<String> roles = jwtUtils.getRolesFromToken(jwt);
            boolean hasRequiredRole = false;

            tokenLogger.info("User {} has roles: {}", username, roles);
            System.out.println("User " + username + " has roles: " + roles);

            // Check the requested URI and assign the required roles
            String requestUri = request.getRequestURI();

            // Determine the required role based on the URI
            if (requestUri.startsWith("/order") || requestUri.startsWith("/recipe") || requestUri.startsWith("/user")) {
                hasRequiredRole = roles.contains("COOK");
            } else if (requestUri.startsWith("/foods") || requestUri.startsWith("/order") || requestUri.startsWith("/order_line")
                    || requestUri.startsWith("/recipe") || requestUri.startsWith("/receipt") || requestUri.startsWith("/user")
                    || requestUri.startsWith("/financial")) {
                hasRequiredRole = roles.contains("CUSTOMER") || roles.contains("ADMIN");
            } else if (requestUri.startsWith("/ingredient")) {
                hasRequiredRole = roles.contains("ADMIN");
            }

            // Check if the user has the required role
            if (hasRequiredRole) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // Credentials are not required for JWT
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                tokenLogger.warn("User {} does not have the required role for this request.", username);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        } catch (Exception e) {
            tokenLogger.error("Cannot set user authentication: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
            return headerAuth.substring(7); // Remove "Bearer " prefix

        return null;
    }
}
