package mouda.backend.common.config.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.business.MemberService;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	private final MemberService memberService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginMember.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory
	) throws Exception {
		String authorizationHeader = webRequest.getHeader("Authorization");

		String token = extractToken(authorizationHeader);
		return memberService.findMember(token);
	}

	private String extractToken(String authorizationHeader) {
		return authorizationHeader.substring(7);
	}
}
