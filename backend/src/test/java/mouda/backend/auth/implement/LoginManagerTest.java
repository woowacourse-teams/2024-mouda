package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class LoginManagerTest {

	@Autowired
	LoginManager loginManager;

	@Autowired
	MemberRepository memberRepository;

	@DisplayName("주어진 카카오 id로 로그인을 시도한다 -> 회원가입한 이력이 있는 경우")
	@Test
	void processSocialLogin() {
		// given
		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		// when
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.KAKAO,
			member.getSocialLoginId());

		// then
		assertThat(loginProcessResult.accessToken()).isNotNull();
		assertThat(loginProcessResult.memberId()).isEqualTo(member.getId());
		Optional<Member> foundMember = memberRepository.findByLoginDetail_SocialLoginId(member.getSocialLoginId());
		assertThat(foundMember.isPresent()).isTrue();
		assertThat(foundMember.get()).isEqualTo(member);
	}

	@DisplayName("이전에 카카오로 가입한 적이 있는 사용자가 카카오 로그인을 시도하면 성공적으로 로그인한다.")
	@Test
	void processSocialLoginWhoSignedUpBefore() {
		// given
		String kakaoId = "456";
		Member anna = MemberFixture.getAnna(kakaoId);
		memberRepository.save(anna);

		// when
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.KAKAO, kakaoId);

		// then
		assertThat(loginProcessResult.accessToken()).isNotNull();
	}

	@DisplayName("이전에 카카오로 가입한 적이 없는 사용자가 카카오 로그인을 시도하면 예외를 발생시킨다.")
	@Test
	void processSocialLoginWhoDoesNotSignedUpBefore() {
		// given
		String kakaoId = "456";

		// when & then
		Assertions.assertThatThrownBy(() -> loginManager.processSocialLogin(OauthType.KAKAO, kakaoId))
			.isInstanceOf(AuthException.class);
	}
}
