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
import mouda.backend.auth.Infrastructure.response.TokenResponse;
import mouda.backend.auth.implement.JwtProvider;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;

@Component
@RequiredArgsConstructor
public class AppleOauthClient implements OauthClient {

	private static final String CLIENT_ID = "ca3adf9a52671fdbb847b809c0fdb980";
	private static final String APPLE_API_URL = "https://appleid.apple.com/auth/token";
	private static final String GRANT_TYPE = "authorization_code";
	
	private final RestClient restClient;
	private final JwtProvider jwtProvider;
	private final MemberFinder memberFinder;

	@Value("${oauth.apple.redirect-uri}")
	private String redirectUri;

	@Override
	public String getIdToken(String code) {
		HttpHeaders headers = getHttpHeaders();
		MultiValueMap<String, String> formData = getFormData(code);
		TokenResponse tokenResponse = restClient.method(HttpMethod.POST)
			.uri(APPLE_API_URL)
			.headers(httpHeaders -> httpHeaders.addAll(headers))
			.body(formData)
			.retrieve()
			.body(TokenResponse.class);
		return tokenResponse.accessToken();
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	private MultiValueMap<String, String> getFormData(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", CLIENT_ID);
		formData.add("client_secret", getClientSecret(code));
		formData.add("code", code);
		formData.add("grant_type", GRANT_TYPE);
		formData.add("redirect_uri", redirectUri);
		return formData;
	}

	private String getClientSecret(String code) {
		long memberId = jwtProvider.extractMemberId(code);
		Member member = memberFinder.find(memberId);
		return jwtProvider.createToken(member);
	}
}
