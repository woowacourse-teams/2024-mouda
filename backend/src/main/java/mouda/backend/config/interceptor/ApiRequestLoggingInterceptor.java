package mouda.backend.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApiRequestLoggingInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) {
		ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper)request;

		String uri = request.getRequestURI();
		String method = request.getMethod();
		String body = cachingRequest.getContentAsString();

		log.info("uri = {}, method = {}, body = {}", uri, method, body);
	}
}
