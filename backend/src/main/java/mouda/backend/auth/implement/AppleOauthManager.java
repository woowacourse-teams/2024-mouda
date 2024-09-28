package mouda.backend.auth.implement;

import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.AppleOauthClient;

@Component
@RequiredArgsConstructor
public class AppleOauthManager {

	private final AppleOauthClient appleOauthClient;

	public String getAppleUserInfo(String code) {
		String idToken = appleOauthClient.getIdToken(code);
		return getSocialLoginId(idToken);
	}

	private String getSocialLoginId(String idToken) {
		String[] parts = idToken.split("\\.");
		String base64encoded = parts[1];

		String body = new String(Base64.getUrlDecoder().decode(base64encoded));

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, Object> payload = objectMapper.readValue(body, Map.class);

			return (String)payload.get("sub");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
