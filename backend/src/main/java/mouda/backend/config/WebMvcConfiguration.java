package mouda.backend.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mouda.backend.config.argumentresolver.LoginMemberArgumentResolver;
import mouda.backend.config.interceptor.AuthenticationCheckInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    private final AuthenticationCheckInterceptor authenticationCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationCheckInterceptor)
            .excludePathPatterns("/v1/auth/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }
}
