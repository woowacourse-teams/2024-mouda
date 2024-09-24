package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

public class DarakbangMemberFinderTest {

	@Mock
	private DarakbangMemberRepository darakbangMemberRepository;

	@InjectMocks
	private DarakbangMemberFinder darakbangMemberFinder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Mock 초기화
	}

	@Test
	@DisplayName("DarakbangMember를 정상적으로 찾는 경우")
	void findDarakbangMember_success() {
		// given
		Darakbang darakbang = new Darakbang("테스트 다락방", "W12TE12");
		Member member = Member.builder()
			.kakaoId(123L)
			.nickname("teabh")
			.build(); // 예시 회원
		DarakbangMember expectedDarakbangMember = DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang,
			member);

		given(darakbangMemberRepository.findByDarakbangIdAndMemberId(darakbang.getId(), member.getId()))
			.willReturn(Optional.of(expectedDarakbangMember));

		// when
		DarakbangMember foundDarakbangMember = darakbangMemberFinder.find(darakbang, member);

		// then
		assertThat(foundDarakbangMember).isEqualTo(expectedDarakbangMember);
	}

	@Test
	@DisplayName("DarakbangMember를 찾지 못한 경우 예외가 발생한다")
	void findDarakbangMember_notFound() {
		// given
		Darakbang darakbang = new Darakbang("테스트 다락방", "W12TE12");
		Member member = Member.builder()
			.kakaoId(123L)
			.nickname("teabh")
			.build(); // 예시 회원
		given(darakbangMemberRepository.findByDarakbangIdAndMemberId(darakbang.getId(), member.getId()))
			.willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> darakbangMemberFinder.find(darakbang, member))
			.isInstanceOf(AuthException.class)
			.hasMessage(AuthErrorMessage.DARAKBANG_NOT_ENTERED.getMessage())
			.hasFieldOrPropertyWithValue("httpStatus", HttpStatus.UNAUTHORIZED);
	}
}
