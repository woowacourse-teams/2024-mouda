package mouda.backend.darakbangmember.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.repository.DarakbangRepository;
import mouda.backend.darakbangmember.dto.response.DarakbangMemberRoleResponse;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.fixture.DarakbangFixture;
import mouda.backend.fixture.DarakbangMemberFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;

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

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("다락방 멤버 조회 테스트")
	@Nested
	class DarakbangMemberReadTest {

		@DisplayName("다락방 관리자가 아니라면 멤버 조회에 실패한다.")
		@Test
		void failToReadWithoutDarakbangManager() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));

			assertThatThrownBy(() -> darakbangMemberService.findAllDarakbangMembers(darakbang.getId(), hogee))
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
			Member hogee = memberRepository.save(MemberFixture.getHogee());

			DarakbangMemberRoleResponse response = darakbangMemberService.findDarakbangMemberRole(
				darakbang.getId(), hogee);

			assertThat(response.role()).isEqualTo("OUTSIDER");
		}
	}
}
