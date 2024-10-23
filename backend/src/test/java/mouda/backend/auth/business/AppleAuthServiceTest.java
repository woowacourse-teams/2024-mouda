package mouda.backend.auth.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.auth.implement.AppleUserInfoProvider;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.MemberStatus;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class AppleAuthServiceTest {

	@Autowired
	private AppleAuthService appleAuthService;

	@Autowired
	private MemberRepository memberRepository;

	@MockBean
	private AppleUserInfoProvider userInfoProvider;

	private final String identifier = "123";
	private final String name = "김민겸";

	@BeforeEach
	void setUp() {
		when(userInfoProvider.getName(anyString())).thenReturn(name);
		when(userInfoProvider.getIdentifier(anyString())).thenReturn(identifier);
	}

	@DisplayName("최초 로그인인 경우 회원 가입과 로그인을 진행한다.")
	@Test
	void joinAndLogin() {
		// when
		LoginResponse response = appleAuthService.login("idToken", "user");

		// then
		assertThat(response.accessToken()).isNotNull();
		Optional<Member> member = memberRepository.findByLoginDetail_Identifier(identifier);
		assertThat(member.isPresent()).isTrue();
		assertThat(member.get().getName()).isEqualTo(name);
	}

	@DisplayName("회원가입 이력이 있는 경우 회원가입 없이 로그인을 진행한다.")
	@Test
	void login() {
		// given
		Member anna = MemberFixture.getAnna(identifier);
		memberRepository.save(anna);

		// when
		LoginResponse response = appleAuthService.login("idToken", null);

		// then
		assertThat(response.accessToken()).isNotNull();
		Optional<Member> member = memberRepository.findByLoginDetail_Identifier(identifier);
		assertThat(member.isPresent()).isTrue();
	}

	@DisplayName("회원 탈퇴 이력이 있는 경우 재가입 후 로그인을 진행한다.")
	@Test
	void rejoinAndLogin() {
		// given
		Member anna = MemberFixture.getAnna(identifier);
		anna.withdraw();
		memberRepository.save(anna);

		// when
		LoginResponse response = appleAuthService.login("idToken", null);

		// then
		assertThat(response.accessToken()).isNotNull();
		Optional<Member> member = memberRepository.findByLoginDetail_Identifier(identifier);
		assertThat(member.isPresent()).isTrue();
		assertThat(member.get().getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
	}
}
