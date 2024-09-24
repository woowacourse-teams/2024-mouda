package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.JwtProvider;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.implement.OauthManager;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Service
@RequiredArgsConstructor
public class KakaoAuthService implements AuthService {

	private final JwtProvider jwtProvider;
	private final OauthManager oauthManager;
	private final LoginManager loginManager;
	private final MemberFinder memberFinder;
	private final MemberWriter memberWriter;

	public LoginResponse oauthLogin(OauthRequest oauthRequest) {
		Long kakaoId = oauthManager.getKakaoId(oauthRequest.code());
		String token = loginManager.processKakaoLogin(kakaoId);
		return new LoginResponse(token);
	}

	public Member findMember(String token) {
		long memberId = jwtProvider.extractMemberId(token);
		return memberFinder.find(memberId);
	}

	public void checkAuthentication(String token) {
		jwtProvider.validateExpiration(token);
	}

	public LoginResponse basicLogin() {
		Member member = new Member("nickname", 1L);
		memberWriter.append(member);
		return new LoginResponse(jwtProvider.createToken(member));
	}
}
