package mouda.backend.auth.implement;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.GoogleOauthClient;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;

@Component
@RequiredArgsConstructor
public class GoogleOauthManager {

	private final GoogleOauthClient googleOauthClient;
	private static final String CLIENT_ID = "630308965506-4eiek02jh2a5fbj7as1o84l4mks3s2tu.apps.googleusercontent.com";

	public String getMemberName(String idToken) {
		GoogleIdToken googleIdToken = getGoogleIdToken(idToken);
		return (String)googleIdToken.getPayload().get("name");
	}

	public String getSocialLoginId(String idToken) {
		GoogleIdToken googleIdToken = getGoogleIdToken(idToken);
		return googleIdToken.getPayload().getSubject();
	}

	private GoogleIdToken getGoogleIdToken(String idToken) {
		GoogleIdToken googleIdToken = validateIdToken(idToken);
		if (googleIdToken == null) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.INVALID_TOKEN);
		}
		return googleIdToken;
	}

	private GoogleIdToken validateIdToken(String idToken) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			.setAudience(Collections.singletonList(CLIENT_ID))
			.build();
		try {
			return verifier.verify(idToken);
		} catch (Exception e) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.INVALID_TOKEN);
		}
	}
}
