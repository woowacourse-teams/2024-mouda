package mouda.backend.auth.Infrastructure;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import mouda.backend.auth.dto.response.OauthResponse;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;

@Component
public class KakaoOauthClient {

	private final RestClient restClient;

	public KakaoOauthClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public String getIdToken(String code) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set(HttpHeaders.ACCEPT_CHARSET, "utf-8");

			MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
			formData.add("code", code);
			formData.add("client_id", "ca3adf9a52671fdbb847b809c0fdb980");
			formData.add("grant_type", "authorization_code");
			formData.add("redirect_uri", "http://localhost:8081/login2");

			OauthResponse response = restClient.method(HttpMethod.POST)
				.uri("/oauth/token")
				.headers(httpHeaders -> httpHeaders.addAll(headers))
				.body(formData)
				.retrieve()
				.body(OauthResponse.class);

			return response.id_token();
		} catch (Exception e) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.KAKAO_UNAUTHORIZED);
		}

	}
}
