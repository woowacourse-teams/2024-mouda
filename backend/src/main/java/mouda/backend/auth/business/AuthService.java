package mouda.backend.auth.business;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.KakaoOauthClient;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.auth.util.JwtProvider;
import mouda.backend.auth.util.TokenDecoder;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberRepository darakbangMemberRepository;
	private final KakaoOauthClient kakaoOauthClient;

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

	public DarakbangMember findDarakbangMember(long darakbangId, Member member) {
		darakbangRepository.findById(darakbangId)
			.orElseThrow(() -> new AuthException(HttpStatus.NOT_FOUND, AuthErrorMessage.DARAKBANG_NOT_FOUND));

		return darakbangMemberRepository.findByDarakbangIdAndMemberId(darakbangId, member.getId())
			.orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.DARAKBANG_NOT_ENTERED));
	}

	public void checkAuthentication(String token) {
		jwtProvider.validateExpiration(token);
	}

	public LoginResponse basicLogin() {
		Member member = new Member("nickname", 1L);
		memberRepository.save(member);
		return new LoginResponse(jwtProvider.createToken(member));
	}
}
