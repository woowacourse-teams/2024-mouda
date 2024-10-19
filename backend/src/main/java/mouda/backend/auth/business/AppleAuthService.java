package mouda.backend.auth.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.Infrastructure.AppleOauthClient;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.implement.AppleOauthManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.controller.AppleUserInfoRequest;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;
import mouda.backend.member.infrastructure.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleAuthService {

	private final MemberRepository memberRepository;
	private final MemberFinder memberFinder;
	private final MemberWriter memberWriter;
	private final AppleOauthManager appleOauthManager;
	private final AccessTokenProvider accessTokenProvider;
	private final ObjectMapper objectMapper;
	private final AppleOauthClient appleOauthClient;

	// TODO: 더 이상 사용하지 않는 로직. 로그인 프로세스 정착 후 제거할 것
	// public LoginResponse oauthLogin(AppleOauthRequest oauthRequest) {
	// 	TODO: 사용자 전환 로직 실행 이전부터 애플 소셜 회원가입이 진행되어있음. 현재 카카오 사용자가 전환을 시도하여 애플 로그인하면 같은 애플 로그인 사용자가 두 명이 되면서 에러가 터질 것.
	// if (oauthRequest.memberId() != null) {
	// 	String accessToken = loginManager.updateOauth(oauthRequest.memberId(), OauthType.APPLE,
	// 		member.getSocialLoginId());
	// 	return new LoginResponse(accessToken);
	// }
	// LoginProcessResult result = loginManager.processAppleLogin(member);
	// return new LoginResponse(result.accessToken());ap
	// }

	public String login(String idToken) {
		String socialLoginId = appleOauthManager.getSocialLoginId(idToken);
		Member member = memberFinder.findBySocialId(socialLoginId);
		reSignup(member);
		return accessTokenProvider.provide(member);
	}

	private void reSignup(Member member) {
		if (member.isDeleted()) {
			member.reSignup();
		}
	}

	public void save(String idToken, String user) {
		try {
			AppleUserInfoRequest request = objectMapper.readValue(user, AppleUserInfoRequest.class);
			String firstName = request.name().firstName();
			String lastName = request.name().lastName();
			saveMember(idToken, firstName, lastName);
		} catch (JsonProcessingException exception) {
			throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.APPLE_USER_BAD_REQUEST);
		}
	}

	private void saveMember(String idToken, String firstName, String lastName) {
		String socialLoginId = appleOauthManager.getSocialLoginId(idToken);
		Member member = new Member(lastName + firstName, new LoginDetail(OauthType.APPLE, socialLoginId));
		memberWriter.append(member);
	}
}
