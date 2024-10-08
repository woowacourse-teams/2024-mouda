package mouda.backend.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMemberArgumentResolver;
import mouda.backend.common.config.argumentresolver.LoginMemberArgumentResolver;
import mouda.backend.common.config.converter.FilterTypeConverter;
import mouda.backend.common.config.interceptor.AuthenticationCheckInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginMemberArgumentResolver loginMemberArgumentResolver;
	private final LoginDarakbangMemberArgumentResolver loginDarakbangMemberArgumentResolver;
	private final AuthenticationCheckInterceptor authenticationCheckInterceptor;
	private final FilterTypeConverter filterTypeConverter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationCheckInterceptor)
			.addPathPatterns("/v1/**")
			.excludePathPatterns("/v1/auth/kakao/oauth", "/v1/auth/login", "/health", "/v1/auth/google/oauth",
				"/v1/auth/apple/oauth");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginMemberArgumentResolver);
		resolvers.add(loginDarakbangMemberArgumentResolver);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(filterTypeConverter);
	}
}
