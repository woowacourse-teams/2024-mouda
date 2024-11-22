package mouda.backend.darakbang.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.domain.Darakbangs;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangFinderTest {

	@Autowired
	private DarakbangFinder darakbangFinder;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@DisplayName("내가 참여한 다락방을 전체 조회한다.")
	@Test
	void findAllMyDarakbangs() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());

		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		Darakbang mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));

		Darakbangs result = darakbangFinder.findAllMyDarakbangs(hogee);

		assertThat(result.getDarakbangs()).hasSize(2);
	}

	@DisplayName("다락방 아이디에 해당하는 다락방을 조회한다.")
	@Test
	void findById() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());

		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		Darakbang mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));

		Darakbang darakbang = darakbangFinder.findById(wooteco.getId());

		assertThat(darakbang).isEqualTo(wooteco);
	}

	@Test
	@DisplayName("Darakbang을 찾지 못한 경우 예외가 발생한다")
	void findDarakbang_notFound() {
		assertThatThrownBy(() -> darakbangFinder.findById(1L))
			.isInstanceOf(DarakbangException.class)
			.hasMessage(DarakbangErrorMessage.DARAKBANG_NOT_FOUND.getMessage())
			.hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);
	}

	@DisplayName("다락방 참여 코드 해당하는 다락방을 조회한다.")
	@Test
	void findByCode() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());

		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		Darakbang mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));

		Darakbang darakbang = darakbangFinder.findByCode(wooteco.getCode());

		assertThat(darakbang).isEqualTo(wooteco);
	}
}
