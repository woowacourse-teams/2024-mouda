package mouda.backend.auth.business;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.auth.implement.AppleOauthManager;
import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class AppleAuthServiceTest {

	@Autowired
	private AppleAuthService appleAuthService;

	@MockBean
	private AppleOauthManager appleOauthManager;

	@MockBean
	private MemberFinder memberFinder;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("애플 로그인 요청을 보내면 access token을 반환한다.")
	@Test
	@Disabled("실제 Resource Server에게 요청을 보내는 테스트이다. 프론트 서버를 켜서 코드르 발급 받아 필요할 때만 테스트한다.")
	void oauthLogin() {
		String code = "";
		AppleOauthRequest oauthRequest = new AppleOauthRequest(1L, "nonce");

		LoginResponse loginResponse = appleAuthService.oauthLogin(oauthRequest);

		assertThat(loginResponse).isNotNull();
	}

	/*
	@DisplayName("사용자 전환을 시도하면 새로운 회원을 추가하지 않고 기존 회원의 정보를 수정한다.")
	@Test
	void oauthLoginConvertingMember() {
		String appleSocialLoginId = "appleSocialLoginId";
		when(appleOauthManager.getSocialLoginId(anyString())).thenReturn(appleSocialLoginId);
		Member anna = memberRepository.save(MemberFixture.getAnna());
		when(memberFinder.findBySocialId(anyString())).thenReturn(anna);
		Long kakaoMemberId = anna.getId();
		LoginResponse loginResponse = appleAuthService.oauthLogin(
			new AppleOauthRequest(kakaoMemberId, "nonce"));

		assertThat(loginResponse).isNotNull();
		Optional<Member> memberOptional = memberRepository.findById(kakaoMemberId);
		assertThat(memberOptional.isPresent()).isTrue();

		LoginDetail expected = new LoginDetail(OauthType.APPLE, appleSocialLoginId);
		assertThat(memberOptional.get().getLoginDetail()).isEqualTo(expected);
	}*/
}
