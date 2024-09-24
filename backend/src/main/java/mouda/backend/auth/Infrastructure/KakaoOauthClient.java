package mouda.backend.auth.Infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.presentation.response.OauthResponse;

@Component
@RequiredArgsConstructor
public class KakaoOauthClient implements OauthClient {

	public static final String CLIENT_ID = "ca3adf9a52671fdbb847b809c0fdb980";
	public static final String GRANT_TYPE = "authorization_code";
	private static final String KAKAO_API_URL = "https://kauth.kakao.com/oauth/token";

	private final RestClient restClient;

	@Value("${oauth.kakao.redirect-uri}")
	private String redirectUri;

	public String getIdToken(String code) {
		try {
			HttpHeaders headers = getHttpHeaders();
			MultiValueMap<String, String> formData = getFormData(code);

			OauthResponse response = restClient.method(HttpMethod.POST)
				.uri(KAKAO_API_URL)
				.headers(httpHeaders -> httpHeaders.addAll(headers))
				.body(formData)
				.retrieve()
				.body(OauthResponse.class);

			return response.id_token();
		} catch (ResourceAccessException e) {
			throw new AuthException(HttpStatus.BAD_GATEWAY, AuthErrorMessage.KAKAO_CONNECT_TIMEOUT);
		} catch (Exception e) {
			throw new AuthException(HttpStatus.BAD_GATEWAY, AuthErrorMessage.KAKAO_UNAUTHORIZED);
		}

	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(HttpHeaders.ACCEPT_CHARSET, "utf-8");
		return headers;
	}

	private MultiValueMap<String, String> getFormData(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("client_id", CLIENT_ID);
		formData.add("grant_type", GRANT_TYPE);
		formData.add("redirect_uri", redirectUri);
		return formData;
	}
}
