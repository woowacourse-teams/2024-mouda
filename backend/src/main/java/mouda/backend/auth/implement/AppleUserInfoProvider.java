package mouda.backend.auth.implement;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.presentation.request.AppleUserInfoRequest;
import mouda.backend.auth.util.TokenDecoder;

@Component
@RequiredArgsConstructor
public class AppleUserInfoProvider {

	private static final String SUB_CLAIM_KEY = "sub";

	private final ObjectMapper objectMapper;

	public String getIdentifier(String idToken) {
		Map<String, String> payload = TokenDecoder.parseIdToken(idToken);
		return payload.get(SUB_CLAIM_KEY);
	}

	public String getName(String user) {
		try {
			AppleUserInfoRequest request = objectMapper.readValue(user, AppleUserInfoRequest.class);
			String firstName = request.name().firstName();
			String lastName = request.name().lastName();
			return firstName + lastName;
		} catch (JsonProcessingException exception) {
			throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.APPLE_USER_BAD_REQUEST);
		}
	}
}
