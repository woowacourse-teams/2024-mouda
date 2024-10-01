package mouda.backend.auth.implement;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.AppleOauthClient;
import mouda.backend.auth.util.TokenDecoder;

@Component
@RequiredArgsConstructor
public class AppleOauthManager {

	private static final String SUB_CLAIM_KEY = "sub";

	private final AppleOauthClient appleOauthClient;

	public String getAppleUserInfo(String code) {
		String idToken = appleOauthClient.getIdToken(code);
		Map<String, String> payload = TokenDecoder.parseIdToken(idToken);

		return payload.get(SUB_CLAIM_KEY);
	}
}
