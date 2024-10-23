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
import mouda.backend.member.domain.MemberStatus;
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
		Member kakao = MemberFixture.getAnna(kakaoIdentifier);
		memberRepository.save(kakao);

		String googleIdentifier = "googleIdentifier";
		Member google = MemberFixture.getAnna(OauthType.APPLE, googleIdentifier);
		memberRepository.save(google);
		when(userInfoProvider.getIdentifier(anyString())).thenReturn(kakaoIdentifier);

		// when
		kakaoAuthService.convert(google, new KakaoConvertRequest("code"));

		// then
		Optional<Member> kakaoMember = memberRepository.findActiveOrDeletedByIdentifier(kakaoIdentifier);
		assertThat(kakaoMember.isPresent()).isTrue();
		assertThat(kakaoMember.get().getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
		Optional<Member> googleMember = memberRepository.findDeprecatedByIdentifier(googleIdentifier);
		assertThat(googleMember.isPresent()).isTrue();
		assertThat(googleMember.get().getMemberStatus()).isEqualTo(MemberStatus.DEPRECATED);
		assertThat(googleMember.get().getLoginDetail()).isEqualTo(google.getLoginDetail());
	}
}

