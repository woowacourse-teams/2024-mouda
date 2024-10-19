package mouda.backend.auth.implement;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.util.TokenDecoder;

@Component
@RequiredArgsConstructor
public class AppleOauthManager {

	private static final String SUB_CLAIM_KEY = "sub";

	public String getIdentifier(String idToken) {
		Map<String, String> payload = TokenDecoder.parseIdToken(idToken);
		return payload.get(SUB_CLAIM_KEY);
	}
}
