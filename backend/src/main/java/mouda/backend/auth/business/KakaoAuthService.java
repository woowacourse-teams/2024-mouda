package mouda.backend.auth.business;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.KakaoUserInfoProvider;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.request.KakaoLoginRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private final AccessTokenProvider accessTokenProvider;
	private final KakaoUserInfoProvider userInfoProvider;
	private final MemberWriter memberWriter;
	private final MemberFinder memberFinder;

	public LoginResponse convert(KakaoLoginRequest kakaoLoginRequest) {
		String identifier = userInfoProvider.getIdentifier(kakaoLoginRequest.code());
		Member member = memberFinder.getByIdentifier(identifier);
		return new LoginResponse(accessTokenProvider.provide(member));
	}

	public LoginResponse basicLoginAnna() {
		Member member = memberFinder.findByIdentifier("testIdentifier");
		return new LoginResponse(accessTokenProvider.provide(member));
	}

	public LoginResponse basicLoginHogee() {
		Member member = Member.builder()
			.name("조호연")
			.loginDetail(new LoginDetail(OauthType.GOOGLE, UUID.randomUUID().toString()))
			.build();
		memberWriter.append(member);
		return new LoginResponse(accessTokenProvider.provide(member));
	}
}
