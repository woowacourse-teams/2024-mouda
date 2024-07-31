package mouda.backend.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mouda.backend.auth.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationCheckInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHORIZATION_PREFIX)) {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }

        String token = extractToken(authorizationHeader);
        authService.checkAuthentication(token);

        return true;
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }
}
