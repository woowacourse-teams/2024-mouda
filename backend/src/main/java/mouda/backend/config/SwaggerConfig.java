package mouda.backend.config;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import mouda.backend.config.argumentresolver.LoginMember;

@Configuration
@SecurityScheme(
	name = "Bearer Authorization",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"
)
public class SwaggerConfig {

	static {
		SpringDocUtils.getConfig().addAnnotationsToIgnore(LoginMember.class);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI();
	}
}
