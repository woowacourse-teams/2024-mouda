package mouda.backend.auth.implement;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;

@Component
@RequiredArgsConstructor
public class GoogleUserInfoProvider {

	private static final String CLIENT_ID = "630308965506-4eiek02jh2a5fbj7as1o84l4mks3s2tu.apps.googleusercontent.com";

	public String getName(String identityToken) {
		GoogleIdToken googleIdToken = getGoogleIdentityToken(identityToken);
		String familyName = (String)googleIdToken.getPayload().get("family_name");
		String givenName = (String)googleIdToken.getPayload().get("given_name");
		return getFullName(familyName, givenName);
	}

	private String getFullName(String familyName, String givenName) {
		if (familyName == null) {
			familyName = "";
		}
		if (givenName == null) {
			givenName = "";
		}
		return familyName + givenName;
	}

	public String getIdentifier(String identityToken) {
		GoogleIdToken googleIdToken = getGoogleIdentityToken(identityToken);
		return googleIdToken.getPayload().getSubject();
	}

	private GoogleIdToken getGoogleIdentityToken(String identityToken) {
		GoogleIdToken googleIdToken = validateIdentityToken(identityToken);
		if (googleIdToken == null) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.INVALID_TOKEN);
		}
		return googleIdToken;
	}

	private GoogleIdToken validateIdentityToken(String identityToken) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			.setAudience(Collections.singletonList(CLIENT_ID))
			.build();
		try {
			return verifier.verify(identityToken);
		} catch (Exception e) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.INVALID_TOKEN);
		}
	}
}
