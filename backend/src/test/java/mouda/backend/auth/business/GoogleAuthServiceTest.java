package mouda.backend.auth.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.auth.implement.GoogleOauthManager;
import mouda.backend.auth.presentation.request.GoogleLoginRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.MemberStatus;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class GoogleAuthServiceTest {

	@Autowired
	private GoogleAuthService googleAuthService;

	@Autowired
	private MemberRepository memberRepository;

	@MockBean
	private GoogleOauthManager googleOauthManager;

	@DisplayName("회원 탈퇴한 사용자가 재가입하는 경우 상태 정보를 변경한다.")
	@Test
	void processSocialLoginWhoDeletedBefore() {
		// given
		Member anna = MemberFixture.getAnna();
		anna.withdraw();
		memberRepository.save(anna);

		when(googleOauthManager.getMemberName(anyString())).thenReturn("anna");
		when(googleOauthManager.getIdentifier(anyString())).thenReturn(anna.getIdentifier());

		// when
		LoginResponse loginResponse = googleAuthService.oauthLogin(new GoogleLoginRequest("IdToken"));

		// then
		assertThat(loginResponse.accessToken()).isNotNull();
		Optional<Member> member = memberRepository.findByLoginDetail_Identifier("1234");
		assertThat(member.isPresent()).isTrue();
		assertThat(member.get().getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
	}
}
