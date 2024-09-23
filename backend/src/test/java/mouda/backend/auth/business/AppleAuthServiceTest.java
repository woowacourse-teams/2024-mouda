package mouda.backend.auth.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.auth.util.JwtProvider;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.business.MemberService;

@SpringBootTest
class AppleAuthServiceTest {

	@Autowired
	private AppleAuthService appleAuthService;

	@Mock
	private JwtProvider jwtProvider;

	@Mock
	private MemberService memberService;

	@DisplayName("사용자를 인증한 후 애플로부터 발급된 토큰을 반환한다.")
	@Test
	void oauthLogin() {
		when(jwtProvider.extractMemberId(anyString())).thenReturn(1L);
		when(memberService.findMember(anyLong())).thenReturn(MemberFixture.getAnna());
		OauthRequest oauthRequest = new OauthRequest("mockCode");

		LoginResponse loginResponse = appleAuthService.oauthLogin(oauthRequest);

		assertThat(loginResponse.accessToken()).isNotNull();
	}
}