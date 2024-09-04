package mouda.backend.darakbangmember.business;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangMemberServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberService darakbangMemberService;

	@DisplayName("다락방 멤버 조회 테스트")
	@Nested
	class DarakbangMemberReadTest {

		@DisplayName("다락방 관리자가 아니라면 멤버 조회에 실패한다.")
		@Test
		void failToReadWithoutDarakbangManager() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			DarakbangMember darakbangHogee = darakbangMemberRepository.save(
				DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));

			assertThatThrownBy(() -> darakbangMemberService.findAllDarakbangMembers(darakbang.getId(), darakbangHogee))
				.isInstanceOf(DarakbangMemberException.class);
		}
	}

	@DisplayName("다락방 멤버 권한 조회 테스트")
	@Nested
	class DarakbangMemberRoleReadTest {

		@DisplayName("다락방 멤버가 아니라면 OUTSIDER를 반환한다.")
		@Test
		void success() {
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			Darakbang mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));

			DarakbangMemberRoleResponse response = darakbangMemberService.findDarakbangMemberRole(
				darakbang.getId(), hogee);

			assertThat(response.role()).isEqualTo("OUTSIDER");
		}
	}
}
