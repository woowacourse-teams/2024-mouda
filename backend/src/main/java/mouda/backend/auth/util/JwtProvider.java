package mouda.backend.auth.util;

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
public class JwtProvider {

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;

	public JwtProvider(
		@Value("${security.jwt.token.secret-key}") String secretKey,
		@Value("${security.jwt.token.expire-length}") long validityInMilliseconds
	) {
		this.secretKey = secretKey;
		this.validityInMilliseconds = validityInMilliseconds;
	}

	public String createToken(Member member) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.claim("id", member.getId())
			.claim("kakaoId", member.getKakaoId())
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public long extractMemberId(String token) {
		Claims claims = getPayload(token);
		return claims.get("id", Long.class);
	}

	public String extractNickname(String token) {
		Claims claims = getPayload(token);
		return claims.get("nickname", String.class);
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

