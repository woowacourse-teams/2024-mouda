package mouda.backend.auth.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.auth.implement.KakaoUserInfoProvider;
import mouda.backend.auth.presentation.request.KakaoConvertRequest;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class KakaoAuthServiceTest {

	@Autowired
	private KakaoAuthService kakaoAuthService;

	@Autowired
	private MemberRepository memberRepository;

	@MockBean
	private KakaoUserInfoProvider userInfoProvider;

	@DisplayName("카카오 회원 정보를 애플 회원으로 변경한다.")
	@Test
	void convert() {
		// given
		String kakaoIdentifier = "kakaoIdentifier";
		Member anna = MemberFixture.getAnna(kakaoIdentifier);
		memberRepository.save(anna);

		String appleIdentifier = "appleIdentifier";
		Member alternation = MemberFixture.getAnna(OauthType.APPLE, appleIdentifier);
		memberRepository.save(alternation);
		when(userInfoProvider.getIdentifier(anyString())).thenReturn(kakaoIdentifier);

		// when
		kakaoAuthService.convert(alternation, new KakaoConvertRequest("code"));

		// then
		Optional<Member> kakaoMember = memberRepository.findByLoginDetail_Identifier(kakaoIdentifier);
		assertThat(kakaoMember.isEmpty()).isTrue();
		Optional<Member> appleMember = memberRepository.findByLoginDetail_Identifier(appleIdentifier);
		assertThat(appleMember.isPresent()).isTrue();
		assertThat(appleMember.get().getName()).isEqualTo(anna.getName());
		assertThat(appleMember.get().getLoginDetail()).isEqualTo(alternation.getLoginDetail());
	}
}
