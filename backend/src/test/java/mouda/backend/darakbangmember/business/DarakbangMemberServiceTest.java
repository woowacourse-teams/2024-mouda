package mouda.backend.darakbangmember.business;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
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

	@DisplayName("다락방 멤버 조회 테스트")
	@Nested
	class DarakbangMemberReadTest {

		@DisplayName("모든 다락방 멤버는 다락방 멤버 목록을 조회할 수 있다.")
		@EnumSource(mode = EnumSource.Mode.INCLUDE, names = {"MANAGER", "MEMBER"})
		@ParameterizedTest
		void failToReadWithoutDarakbangManager(DarakBangMemberRole darakBangMemberRole) {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			DarakbangMember darakbangMember = DarakbangMember.builder()
				.darakbang(darakbang)
				.memberId(hogee.getId())
				.nickname("소소파파")
				.role(darakBangMemberRole)
				.build();
			DarakbangMember darakbangHogee = darakbangMemberRepository.save(darakbangMember);

			DarakbangMemberResponses responses = darakbangMemberService.findAllDarakbangMembers(
				darakbang.getId(), darakbangHogee);

			assertThat(responses.responses()).hasSize(1);
		}
	}
}
