package mouda.backend.auth.business;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Service
@RequiredArgsConstructor
public class TestAuthService {

	private final MemberFinder memberFinder;
	private final MemberWriter memberWriter;
	private final AccessTokenProvider accessTokenProvider;

	public LoginResponse basicLoginAnna() {
		Member member = memberFinder.findByIdentifier("identifier");
		return new LoginResponse(accessTokenProvider.provide(member), true);
	}

	public LoginResponse basicLoginHogee() {
		Member member = Member.builder()
			.name("조호연")
			.loginDetail(new LoginDetail(OauthType.GOOGLE, UUID.randomUUID().toString()))
			.build();
		memberWriter.append(member);
		return new LoginResponse(accessTokenProvider.provide(member), true);
	}
}
