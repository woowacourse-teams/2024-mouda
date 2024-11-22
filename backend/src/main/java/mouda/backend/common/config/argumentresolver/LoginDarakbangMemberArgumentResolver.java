package mouda.backend.common.config.argumentresolver;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.business.DarakbangMemberService;
import mouda.backend.member.domain.Member;

@Component
@RequiredArgsConstructor
public class LoginDarakbangMemberArgumentResolver implements HandlerMethodArgumentResolver {

	private final LoginMemberArgumentResolver loginMemberArgumentResolver;
	private final DarakbangMemberService darakbangMemberService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginDarakbangMember.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory
	) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Map<String, String> variables = (Map<String, String>)request.getAttribute(
			HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long darakbangId = Long.parseLong(variables.get("darakbangId"));
		Member member = (Member)loginMemberArgumentResolver.resolveArgument(
			parameter, mavContainer, webRequest, binderFactory);

		return darakbangMemberService.findDarakbangMember(darakbangId, member);
	}
}
