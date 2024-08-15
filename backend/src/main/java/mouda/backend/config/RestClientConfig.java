package mouda.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

	@Bean
	public RestClient kakaoOauthRestClient() {
		return RestClient.builder()
			.baseUrl("https://kauth.kakao.com")
			.build();
	}
}
