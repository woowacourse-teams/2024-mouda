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

	// TODO : 단위 테스트 작성 -> 외부 API는 어떻게 테스트하는 것이 좋은가? 해야하는가?
	public Long getKakaoId(String code) {
		String idToken = kakaoOauthClient.getIdToken(code);
		Map<String, String> payload = TokenDecoder.parseIdToken(idToken);
		return Long.parseLong(payload.get("sub"));
	}
}
