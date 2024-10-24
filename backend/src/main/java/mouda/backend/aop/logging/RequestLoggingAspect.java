package mouda.backend.aop.logging;

import static java.util.stream.Collectors.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.domain.Member;

@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {

	@Pointcut("execution(* mouda.backend..controller.*Controller.*(..))")
	public void allController() {
	}

	@Before("allController()")
	public void logController(JoinPoint joinPoint) {
		HttpServletRequest request = getHttpServletRequest();

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		if (method.isAnnotationPresent(ExceptRequestLogging.class)) {
			return;
		}

		String uri = request.getRequestURI();
		String httpMethod = request.getMethod();
		String queryParameters = getQueryParameters(request);
		String body = getBody(joinPoint);

		String memberInfo = getMemberInfo(joinPoint);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Request : %s %s", httpMethod, uri));
		if (memberInfo != null) {
			stringBuilder.append(String.format(", member : %s", memberInfo));
		}
		if (body != null) {
			stringBuilder.append(String.format(", body : %s", body));
		}
		if (queryParameters != null) {
			stringBuilder.append(String.format(", parameters : %s", queryParameters));
		}
		log.info(stringBuilder.toString());
	}

	private String getMemberInfo(JoinPoint joinPoint) {
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof Member) {
				return "Member ID = " + ((Member)arg).getId();
			}
			if (arg instanceof DarakbangMember) {
				return "DarakbangMember ID = " + ((DarakbangMember)arg).getId();
			}
		}
		return null;
	}

	private HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		return requestAttributes.getRequest();
	}

	private String getQueryParameters(HttpServletRequest request) {
		String queryParameters = request.getParameterMap()
			.entrySet()
			.stream()
			.map(entry -> "%s = %s".formatted(entry.getKey(), entry.getValue()[0]))
			.collect(joining(", "));

		if (queryParameters.isEmpty()) {
			return null;
		}
		return queryParameters;
	}

	private String getBody(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Parameter[] parameters = methodSignature.getMethod().getParameters();
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < parameters.length; i++) {
			Parameter param = parameters[i];
			Object arg = args[i];
			if (param.isAnnotationPresent(RequestBody.class)) {
				return arg.toString();
			}
		}
		return null;
	}
}
