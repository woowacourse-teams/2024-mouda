package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class LoginManagerTest {

	@Autowired
	LoginManager loginManager;

	@Autowired
	MemberRepository memberRepository;

	@DisplayName("주어진 카카오 id로 로그인을 시도한다 -> 회원가입한 이력이 있는 경우")
	@Test
	void processKakaoLogin() {
		// given
		Member member = Member.builder()
			.nickname("테바") // TODO : 필드 삭제
			.kakaoId(123L)
			.build();
		memberRepository.save(member);

		// when
		String token = loginManager.processKakaoLogin(member.getKakaoId());

		// then
		assertThat(token).isNotNull();
		Optional<Member> foundMember = memberRepository.findByKakaoId(member.getKakaoId());
		assertThat(foundMember.isPresent()).isTrue();
		assertThat(foundMember.get()).isEqualTo(member);
	}

	@DisplayName("주어진 카카오 id로 로그인을 시도한다 -> 회원가입한 이력이 없는 경우")
	@Test
	void processKakaoLoginWithSignUp() {
		// given
		long kakaoId = 456L;

		// when
		String token = loginManager.processKakaoLogin(kakaoId);

		// then
		assertThat(token).isNotNull();
		Optional<Member> newMember = memberRepository.findByKakaoId(kakaoId);
		assertThat(newMember.isPresent()).isTrue();
	}
}
