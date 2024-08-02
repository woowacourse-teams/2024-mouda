package mouda.backend.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mouda.backend.config.argumentresolver.LoginMemberArgumentResolver;
import mouda.backend.config.converter.FilterTypeConverter;
import mouda.backend.config.interceptor.AuthenticationCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final AuthenticationCheckInterceptor authenticationCheckInterceptor;
    private final FilterTypeConverter filterTypeConverter;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationCheckInterceptor)
            .addPathPatterns("/v1/**")
            .excludePathPatterns("/v1/auth/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(filterTypeConverter);
    }
}
