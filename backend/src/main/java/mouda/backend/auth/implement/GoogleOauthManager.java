package mouda.backend.auth.implement;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.GoogleOauthClient;
import mouda.backend.auth.util.TokenDecoder;

@Component
@RequiredArgsConstructor
public class GoogleOauthManager {

	private final GoogleOauthClient googleOauthClient;

	public String getMemberName(String code) {
		String idToken = googleOauthClient.getIdToken(code);
		Map<String, String> idTokenToMap = TokenDecoder.parseIdToken(idToken);
		return idTokenToMap.get("name");
	}

	public String getSocialLoginId(String code) {
		String idToken = googleOauthClient.getIdToken(code);
		Map<String, String> idTokenToMap = TokenDecoder.parseIdToken(idToken);
		return idTokenToMap.get("sub");
	}
}
