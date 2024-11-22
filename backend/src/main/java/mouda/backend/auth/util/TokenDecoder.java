package mouda.backend.auth.util;

import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;

public class TokenDecoder {

	public static Map<String, String> parseIdToken(String idToken) {
		try {
			String[] parts = idToken.split("\\.");
			if (parts.length != 3) {
				throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.INVALID_TOKEN);
			}
			String payload = parts[1];
			byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
			String decodedPayload = new String(decodedBytes);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(decodedPayload, Map.class);
		} catch (Exception e) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.KAKAO_VALIDATION_FAILED);
		}
	}
}
