package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.AppleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleAuthService {

	private final LoginManager loginManager;
	private final MemberFinder memberFinder;
	private final MemberWriter memberWriter;
	private final AppleOauthManager appleOauthManager;
	private final AccessTokenProvider accessTokenProvider;

	public LoginResponse oauthLogin(AppleOauthRequest oauthRequest) {
		Member member = memberFinder.findByNonce(oauthRequest.nonce());
		// TODO: 사용자 전환 로직 실행 이전부터 애플 소셜 회원가입이 진행되어있음. 현재 카카오 사용자가 전환을 시도하여 애플 로그인하면 같은 애플 로그인 사용자가 두 명이 되면서 에러가 터질 것.
		if (oauthRequest.memberId() != null) {
			String accessToken = loginManager.updateOauth(oauthRequest.memberId(), OauthType.APPLE,
				member.getSocialLoginId());
			return new LoginResponse(accessToken);
		}
		LoginProcessResult result = loginManager.processAppleLogin(member);
		return new LoginResponse(result.accessToken());
	}

	public String save(String idToken, String firstName, String lastName) {
		log.info("idToken: {}, firstName: {}, lastName: {}", idToken, firstName, lastName);
		String socialLoginId = appleOauthManager.getSocialLoginId(idToken);
		Member member = new Member(lastName + firstName, new LoginDetail(OauthType.APPLE, socialLoginId));
		Member savedMember = memberWriter.append(member);
		return accessTokenProvider.provide(savedMember);
	}
}
