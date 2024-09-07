package mouda.backend.common.config.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.business.AuthService;

@Component
@RequiredArgsConstructor
public class AuthenticationCheckInterceptor implements HandlerInterceptor {

	private static final String AUTHORIZATION_PREFIX = "Bearer ";

	private final AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (CorsUtils.isPreFlightRequest(request)) {
			return true;
		}

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHORIZATION_PREFIX)) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED);
		}

		String token = extractToken(authorizationHeader);
		authService.checkAuthentication(token);
		return true;
	}

	private String extractToken(String authorizationHeader) {
		return authorizationHeader.substring(7);
	}
}
