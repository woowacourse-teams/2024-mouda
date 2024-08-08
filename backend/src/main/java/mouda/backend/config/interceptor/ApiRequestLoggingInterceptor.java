package mouda.backend.config.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApiRequestLoggingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		IOException {
		String uri = request.getRequestURI();
		String method = request.getMethod();
		String body = getRequestBody(request);

		log.info("uri = {}, method = {}, body = {}", uri, method, body);
		return true;
	}

	private String getRequestBody(HttpServletRequest request) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
	}
}
