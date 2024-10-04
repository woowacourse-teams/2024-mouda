package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		String token = loginManager.processSocialLogin(OauthType.KAKAO, member.getSocialLoginId());

		// then
		assertThat(token).isNotNull();
		Optional<Member> foundMember = memberRepository.findByLoginDetail_SocialLoginId(member.getSocialLoginId());
		assertThat(foundMember.isPresent()).isTrue();
		assertThat(foundMember.get()).isEqualTo(member);
	}

	@DisplayName("주어진 카카오 id로 로그인을 시도한다 -> 회원가입한 이력이 없는 경우")
	@Test
	void processSocialLoginWithSignUp() {
		// given
		String kakaoId = "456";

		// when
		String token = loginManager.processSocialLogin(OauthType.KAKAO, kakaoId);

		// then
		assertThat(token).isNotNull();
		Optional<Member> newMember = memberRepository.findByLoginDetail_SocialLoginId(kakaoId);
		assertThat(newMember.isPresent()).isTrue();
	}
}
