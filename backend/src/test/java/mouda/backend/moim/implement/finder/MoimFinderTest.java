package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimOverview;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.infrastructure.ZzimRepository;

@SpringBootTest
class MoimFinderTest extends DarakbangSetUp {

	@Autowired
	private MoimFinder moimFinder;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private ZzimRepository zzimRepository;

	@DisplayName("모임을 조회한다.")
	@Nested
	class ReadTest {

		@DisplayName("모임을 조회한다.")
		@Test
		void read() {
			Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			Moim actual = moimFinder.read(moim.getId(), darakbang.getId());

			assertThat(moim.getId()).isEqualTo(actual.getId());
		}

		@DisplayName("모임이 존재하지 않으면 예외가 발생한다.")
		@Test
		void read_notExist() {
			assertThatThrownBy(() -> moimFinder.read(1L, darakbang.getId()))
				.isInstanceOf(MoimException.class)
				.hasMessage(MoimErrorMessage.NOT_FOUND.getMessage());
		}
	}

	@DisplayName("모든 모임을 조회한다.")
	@Test
	void readAll() {
		Moim coffeeMoim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Moim soccerMoim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));

		zzimRepository.save(Zzim.builder().moim(coffeeMoim).darakbangMember(darakbangHogee).build());

		List<MoimOverview> moimOverviews = moimFinder.readAll(darakbang.getId(), darakbangHogee);

		assertThat(moimOverviews).extracting(moimOverview -> moimOverview.getMoim().getId())
			.containsExactly(soccerMoim.getId(), coffeeMoim.getId());
		assertThat(moimOverviews).extracting(MoimOverview::isZzimed)
			.containsExactly(false, true);
	}

	@DisplayName("내가 참여한 모임을 조회한다.")
	@Nested
	class ReadAllMyMoimTest {

		@DisplayName("내가 참여한 모임을 조회한다.")
		@Test
		void readAllMyMoim() {
			Moim coffeeMoim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			chamyoRepository.save(Chamyo.builder()
				.moim(coffeeMoim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMEE)
				.build()
			);

			Moim soccerMoim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));

			List<MoimOverview> moimOverviews = moimFinder.readAllMyMoim(darakbangHogee, FilterType.ALL);

			assertThat(moimOverviews).hasSize(1)
				.extracting(MoimOverview::getMoim)
				.extracting(Moim::getId)
				.containsExactly(coffeeMoim.getId());
		}

		@DisplayName("내가 참여한 모임을 조회한다. (예정 모임)")
		@Test
		void readAllMyMoim_upcoming() {
			Moim coffeeMoim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			chamyoRepository.save(Chamyo.builder()
				.moim(coffeeMoim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMEE)
				.build()
			);

			Moim soccerMoim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
			chamyoRepository.save(Chamyo.builder()
				.moim(soccerMoim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMEE)
				.build()
			);

			List<MoimOverview> moimOverviews = moimFinder.readAllMyMoim(darakbangHogee, FilterType.UPCOMING);

			assertThat(moimOverviews).hasSize(2)
				.extracting(MoimOverview::getMoim)
				.extracting(Moim::getId)
				.containsExactly(soccerMoim.getId(), coffeeMoim.getId());
		}
	}


	@DisplayName("내가 찜한 모임을 조회한다.")
	@Test
	void readAllZzimedMoim() {
		Moim coffeeMoim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Moim soccerMoim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));

		zzimRepository.save(Zzim.builder()
			.moim(coffeeMoim)
			.darakbangMember(darakbangHogee)
			.build()
		);

		zzimRepository.save(Zzim.builder()
			.moim(soccerMoim)
			.darakbangMember(darakbangHogee)
			.build()
		);

		List<MoimOverview> moimOverviews = moimFinder.readAllZzimedMoim(darakbangHogee);

		assertThat(moimOverviews).hasSize(2)
			.extracting(MoimOverview::getMoim)
			.extracting(Moim::getId)
			.containsExactly(soccerMoim.getId(), coffeeMoim.getId());
	}

	@DisplayName("모임의 현재 참여 인원을 조회한다.")
	@Test
	void countCurrentPeople() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		chamyoRepository.save(Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangHogee)
			.moimRole(MoimRole.MOIMEE)
			.build()
		);
		chamyoRepository.save(Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMEE)
			.build()
		);

		int currentPeople = moimFinder.countCurrentPeople(moim);

		assertThat(currentPeople).isEqualTo(2);
	}
}
