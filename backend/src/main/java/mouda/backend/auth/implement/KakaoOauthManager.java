package mouda.backend.auth.implement;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.KakaoOauthClient;
import mouda.backend.auth.util.TokenDecoder;

@Component
@RequiredArgsConstructor
public class KakaoOauthManager {

	private final KakaoOauthClient kakaoOauthClient;

	public String getKakaoId(String code) {
		String idToken = kakaoOauthClient.getIdToken(code);
		Map<String, String> payload = TokenDecoder.parseIdToken(idToken);
		return payload.get("sub");
	}
}
