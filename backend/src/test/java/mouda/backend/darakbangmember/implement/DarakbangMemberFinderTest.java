package mouda.backend.darakbangmember.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMembers;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangMemberFinderTest {

	@Autowired
	private DarakbangMemberFinder darakbangMemberFinder;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@DisplayName("회원이 속한 다락방 목록을 조회한다.")
	@Test
	void findAllByMember() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());

		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		Darakbang mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));

		List<Darakbang> darakbangMembers = darakbangMemberFinder.findAllByMember(hogee);

		assertThat(darakbangMembers).hasSize(2);
	}

	@DisplayName("다락방에 속한 전체 다락방 멤버를 조회한다.")
	@Test
	void findAllDarakbangMembers() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Member anna = memberRepository.save(MemberFixture.getAnna());

		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		DarakbangMember hogee1 = DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco,
			hogee);
		darakbangMemberRepository.save(hogee1);
		DarakbangMember anna1 = DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco,
			anna);
		darakbangMemberRepository.save(anna1);

		DarakbangMembers darakbangMembers = darakbangMemberFinder.findAllDarakbangMembers(wooteco.getId());

		assertThat(darakbangMembers.getDarakbangMembers()).hasSize(2);
		assertThat(darakbangMembers.getDarakbangMembers().get(0)).isEqualTo(hogee1);
		assertThat(darakbangMembers.getDarakbangMembers().get(1)).isEqualTo(anna1);
	}

	@DisplayName("다락방 회원의 권한을 조회한다.")
	@Test
	void findDarakbangMemberRole() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());

		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		DarakBangMemberRole darakbangMemberRole = darakbangMemberFinder.findDarakbangMemberRole(wooteco.getId(),
			hogee.getId());

		assertThat(darakbangMemberRole).isEqualTo(DarakBangMemberRole.MEMBER);
	}

	@Test
	@DisplayName("DarakbangMember를 정상적으로 찾는 경우")
	void findDarakbangMember_success() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		DarakbangMember darakbangMember = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		DarakbangMember foundDarakbangMember = darakbangMemberFinder.find(wooteco, hogee);

		assertThat(foundDarakbangMember).isEqualTo(darakbangMember);
	}

	@Test
	@DisplayName("DarakbangMember를 찾지 못한 경우 예외가 발생한다")
	void findDarakbangMember_notFound() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());

		assertThatThrownBy(() -> darakbangMemberFinder.find(wooteco, hogee))
			.isInstanceOf(DarakbangMemberException.class)
			.hasMessage(DarakbangMemberErrorMessage.MEMBER_NOT_EXIST.getMessage())
			.hasFieldOrPropertyWithValue("httpStatus", HttpStatus.UNAUTHORIZED);
	}
}
