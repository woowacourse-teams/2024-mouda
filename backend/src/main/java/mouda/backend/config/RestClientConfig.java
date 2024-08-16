package mouda.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

	@Bean
	public RestClient kakaoOauthRestClient() {
		return RestClient.builder()
			.requestFactory(getClientHttpRequestFactory())
			.baseUrl("https://kauth.kakao.com")
			.build();
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(100);
		clientHttpRequestFactory.setConnectionRequestTimeout(70);
		return clientHttpRequestFactory;
	}
}
