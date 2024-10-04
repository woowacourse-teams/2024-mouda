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

@Component
public class AccessTokenProvider {

	public static final String MEMBER_ID_CLAIM_KEY = "id";
	public static final String SOCIAL_LOGIN_ID_CLAIM_KEY = "socialLoginId";

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
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public long extractMemberId(String token) {
		Claims claims = getPayload(token);
		return claims.get(MEMBER_ID_CLAIM_KEY, Long.class);
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

	public void validateExpiration(String token) {
		Claims claims = getPayload(token);

		if (claims.getExpiration().before(new Date())) {
			throw new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED);
		}
	}
}

