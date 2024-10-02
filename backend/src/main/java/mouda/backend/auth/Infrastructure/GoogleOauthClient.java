package mouda.backend.auth.Infrastructure;

import static mouda.backend.auth.exception.AuthErrorMessage.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.presentation.response.OauthResponse;

@Component
@RequiredArgsConstructor
public class GoogleOauthClient implements OauthClient {

	private static final String CLIENT_ID = "630308965506-4eiek02jh2a5fbj7as1o84l4mks3s2tu.apps.googleusercontent.com";
	private static final String GRANT_TYPE = "authorization_code";
	private static final String GOOGLE_API_URL = "https://oauth2.googleapis.com/token";

	@Value("${oauth.google.client-secret}")
	private String clientSecret;

	@Value("${oauth.google.redirect-url}")
	private String redirectUrl;

	private final RestClient restClient;

	@Override
	public String getIdToken(String code) {
		try {
			HttpHeaders headers = getHttpHeaders();
			MultiValueMap<String, String> formData = getFormData(code);

			OauthResponse oauthResponse = restClient.method(HttpMethod.POST)
				.uri(GOOGLE_API_URL)
				.headers(httpHeaders -> httpHeaders.addAll(headers))
				.body(formData)
				.retrieve()
				.body(OauthResponse.class);
			return oauthResponse.id_token();
		} catch (Exception e) {
			throw new AuthException(HttpStatus.BAD_GATEWAY, TOKEN_ISSUE_FAILED);
		}
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	private MultiValueMap<String, String> getFormData(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", CLIENT_ID);
		formData.add("client_secret", clientSecret);
		formData.add("code", code);
		formData.add("grant_type", GRANT_TYPE);
		formData.add("redirect_uri", redirectUrl);
		return formData;
	}
}
