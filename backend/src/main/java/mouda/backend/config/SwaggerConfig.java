package mouda.backend.config;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import mouda.backend.config.argumentresolver.LoginMember;

@Configuration
public class SwaggerConfig {

	static {
		SpringDocUtils.getConfig().addAnnotationsToIgnore(LoginMember.class);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().addSecurityItem(
				new SecurityRequirement().addList("Bearer Authorization"))
			.components(new Components().addSecuritySchemes(
				"Bearer Authorization", createAPIKeyScheme()
			));
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
			.bearerFormat("JWT")
			.scheme("bearer");
	}
}
