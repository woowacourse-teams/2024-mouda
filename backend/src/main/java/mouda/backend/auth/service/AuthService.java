package mouda.backend.auth.service;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mouda.backend.auth.Infrastructure.KakaoOauthClient;
import mouda.backend.auth.dto.request.OauthRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.util.TokenDecoder;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.security.JwtProvider;

@Service
public class AuthService {

	private final JwtProvider jwtProvider;

	private final MemberRepository memberRepository;

	private final KakaoOauthClient kakaoOauthClient;

	public AuthService(JwtProvider jwtProvider, MemberRepository memberRepository, KakaoOauthClient kakaoOauthClient) {
		this.jwtProvider = jwtProvider;
		this.memberRepository = memberRepository;
		this.kakaoOauthClient = kakaoOauthClient;
	}

	public LoginResponse oauthLogin(OauthRequest oauthRequest) {
		String kakaoIdToken = kakaoOauthClient.getIdToken(oauthRequest.code());

		Map<String, String> payload = TokenDecoder.parseKakaoToken(kakaoIdToken);
		String kakaoId = payload.get("sub");

		return processLogin(Long.parseLong(kakaoId));
	}

	private LoginResponse processLogin(Long kakaoId) {
		return memberRepository.findByKakaoId(kakaoId)
			.map(member -> {
				String token = jwtProvider.createToken(member);
				return new LoginResponse(token);
			})
			.orElseGet(() -> {
				Member newMember = Member.builder()
					.kakaoId(kakaoId)
					.build();
				memberRepository.save(newMember);
				String token = jwtProvider.createToken(newMember);
				return new LoginResponse(token);
			});
	}

	public Member findMember(String token) {
		long memberId = jwtProvider.extractMemberId(token);
		return memberRepository.findById(memberId)
			.orElseThrow(
				() -> new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED));
	}

	public void checkAuthentication(String token) {
		jwtProvider.validateExpiration(token);
	}
}
