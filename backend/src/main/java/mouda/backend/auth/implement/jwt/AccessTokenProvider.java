package mouda.backend.auth.implement.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

@Component
public class AccessTokenProvider {

	private static final String MEMBER_ID_CLAIM_KEY = "id";
	private static final String SOCIAL_LOGIN_ID_CLAIM_KEY = "socialLoginId";
	private static final String OAUTH_TYPE = "oauthType";

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;

	public String provide(Member member) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.claim(MEMBER_ID_CLAIM_KEY, member.getId())
			.claim(SOCIAL_LOGIN_ID_CLAIM_KEY, member.getSocialLoginId())
			.claim(OAUTH_TYPE, member.getOauthType())
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String extractSocialId(String token) {
		Claims claims = getPayload(token);
		return claims.get(SOCIAL_LOGIN_ID_CLAIM_KEY, String.class);
	}

	public Claims getPayload(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();

		} catch (JwtException | IllegalArgumentException e) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED);
		}
	}

	public void validateToken(String token) {
		Claims claims = getPayload(token);

		if (claims.getExpiration().before(new Date())) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED);
		}
		if (claims.get(OAUTH_TYPE).equals(OauthType.KAKAO.toString())) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED);
		}
	}
}

