package mouda.backend.auth.implement;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.KakaoOauthClient;
import mouda.backend.auth.util.TokenDecoder;

@Component
@RequiredArgsConstructor
public class KakaoUserInfoProvider {

	private static final String SUB_CLAIM_KEY = "sub";

	private final KakaoOauthClient oauthClient;

	public String getIdentifier(String code) {
		String idToken = oauthClient.getIdToken(code);
		Map<String, String> payload = TokenDecoder.parseIdToken(idToken);
		return payload.get(SUB_CLAIM_KEY);
	}
}
