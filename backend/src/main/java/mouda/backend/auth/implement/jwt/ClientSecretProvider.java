package mouda.backend.auth.implement.jwt;

import static mouda.backend.auth.Infrastructure.AppleOauthClient.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;

@Component
public class ClientSecretProvider {

	private static final String ALG_PARAMETER_NAME = "alg";
	private static final String ALG_PARAMETER_VALUE = "ES256";

	private static final String KEY_ID_PARAMETER_NAME = "kid";
	private static final String KEY_ID_PARAMETER_VALUE = "4YYCNG8SC9";

	private static final String APPLE_URL = "https://appleid.apple.com";
	private static final String TEAM_ID = "3D7CZ9274W";

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;

	public String provide() {
		try {
			Date now = new Date();
			Date validity = new Date(now.getTime() + validityInMilliseconds);

			return Jwts.builder()
				.setHeaderParam(ALG_PARAMETER_NAME, ALG_PARAMETER_VALUE)
				.setHeaderParam(KEY_ID_PARAMETER_NAME, KEY_ID_PARAMETER_VALUE)
				.setSubject(CLIENT_ID)
				.setIssuer(TEAM_ID)
				.setAudience(APPLE_URL)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.ES256, getPrivateKey())
				.compact();
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException exception) {
			throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, AuthErrorMessage.TOKEN_ISSUE_FAILED);
		}
	}

	private static PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		InputStream privateKey = new ClassPathResource("auth/AuthKey.p8").getInputStream();

		String result = new BufferedReader(new InputStreamReader(privateKey)).lines().collect(Collectors.joining("\n"));

		String key = result.replace("-----BEGIN PRIVATE KEY-----\n", "")
			.replace("-----END PRIVATE KEY-----", "");

		byte[] encoded = Base64.decodeBase64(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return keyFactory.generatePrivate(keySpec);
	}
}
