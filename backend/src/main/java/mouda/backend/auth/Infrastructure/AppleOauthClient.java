package mouda.backend.auth.Infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.jwt.ClientSecretProvider;
import mouda.backend.auth.presentation.response.OauthResponse;

@Component
@RequiredArgsConstructor
public class AppleOauthClient implements OauthClient {

	public static final String CLIENT_ID = "site.mouda.backend";
	private static final String APPLE_API_URL = "https://appleid.apple.com/auth";
	private static final String GRANT_TYPE = "authorization_code";

	private final RestClient restClient;
	private final ClientSecretProvider clientSecretProvider;

	@Value("${oauth.apple.redirect-uri}")
	private String redirectUri;

	@Override
	public String getIdToken(String code) {
		String tokenUrl = APPLE_API_URL + "/token";
		MultiValueMap<String, String> formData = getFormData(code);

		OauthResponse oauthResponse = restClient.method(HttpMethod.POST)
			.uri(tokenUrl)
			.headers(httpHeaders -> httpHeaders.addAll(getHttpHeaders()))
			.body(formData)
			.retrieve()
			.body(OauthResponse.class);
		return oauthResponse.id_token();
	}

	public void revoke(String refreshToken) {
		String revokeUrl = APPLE_API_URL + "/oauth2/v2/revoke";
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", CLIENT_ID);
		formData.add("client_secret", clientSecretProvider.provide());
		formData.add("token", refreshToken);
		formData.add("token_hint_type", "refresh_token");

		restClient.method(HttpMethod.POST)
			.uri(revokeUrl)
			.headers(httpHeaders -> httpHeaders.addAll(getHttpHeaders()))
			.body(formData);
	}

	private MultiValueMap<String, String> getFormData(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", CLIENT_ID);
		formData.add("client_secret", clientSecretProvider.provide());
		formData.add("code", code);
		formData.add("grant_type", GRANT_TYPE);
		formData.add("redirect_uri", redirectUri);
		return formData;
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}
}
