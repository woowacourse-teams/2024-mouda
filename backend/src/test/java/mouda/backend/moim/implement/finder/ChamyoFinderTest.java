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
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChamyoFinderTest extends DarakbangSetUp {

	@Autowired
	private ChamyoFinder chamyoFinder;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@DisplayName("모임 참여 여부를 조회한다.")
	@Nested
	class ReadTest {

		@DisplayName("모임 참여 여부를 조회한다.")
		@Test
		void read() {
			Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			Chamyo expected = chamyoRepository.save(Chamyo.builder()
				.moim(moim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMEE)
				.build());

			Chamyo actual = chamyoFinder.read(moim, darakbangHogee);

			assertThat(actual.getId()).isEqualTo(expected.getId());
		}

		@DisplayName("참여 정보가 존재하지 않으면 예외가 발생한다.")
		@Test
		void read_() {
			Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			chamyoRepository.save(Chamyo.builder()
				.moim(moim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMEE)
				.build());

			assertThatThrownBy(() -> chamyoFinder.read(moim, darakbangAnna))
				.isInstanceOf(ChamyoException.class);
		}
	}

	@Test
	void exists() {
	}

	@DisplayName("모임 참여자의 역할을 조회한다.")
	@Nested
	class ReadMoimRoleTest {

		@DisplayName("현재 회원은 모이머(방장) 이다.")
		@Test
		void readMoimRole_whenMemberIsMoimer() {
			Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			Chamyo chamyo = chamyoRepository.save(Chamyo.builder()
				.moim(moim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMER)
				.build());

			MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangHogee);
			assertThat(moimRole).isEqualTo(MoimRole.MOIMER);
		}

		@DisplayName("현재 회원은 모이미(참여자) 이다.")
		@Test
		void readMoimRole_whenMemberIsMoimee() {
			Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
			chamyoRepository.save(Chamyo.builder()
				.moim(moim)
				.darakbangMember(darakbangHogee)
				.moimRole(MoimRole.MOIMEE)
				.build());

			MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangHogee);
			assertThat(moimRole).isEqualTo(MoimRole.MOIMEE);
		}

		@DisplayName("현재 회원은 모임에 참여하지 않았다.")
		@Test
		void readMoimRole_whenMemberIsNonMoimee() {
			Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));

			MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangHogee);
			assertThat(moimRole).isEqualTo(MoimRole.NON_MOIMEE);
		}
	}

	@DisplayName("모임 참여 정보를 조회한다.")
	@Test
	void readAll() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo1 = chamyoRepository.save(Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangHogee)
			.moimRole(MoimRole.MOIMEE)
			.build());

		Chamyo chamyo2 = chamyoRepository.save(Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build());

		List<Chamyo> chamyos = chamyoFinder.readAll(moim);
		assertThat(chamyos).extracting("id")
			.containsExactly(chamyo1.getId(), chamyo2.getId());
	}

	@DisplayName("채팅방이 열린 모든 참여 정보를 조회한다.")
	@Test
	void readAllChatOpened() {
		Moim coffeeMoim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo coffeeMoimChamyo = chamyoRepository.save(Chamyo.builder()
			.moim(coffeeMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMEE)
			.build());
		coffeeMoim.openChat();
		moimRepository.save(coffeeMoim);

		Moim basketballMoim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		Chamyo basketballMoimChamyo = chamyoRepository.save(Chamyo.builder()
			.moim(basketballMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMEE)
			.build());

		List<Chamyo> chamyos = chamyoFinder.readAllChatOpened(darakbang.getId(), darakbangAnna);
		assertThat(chamyos).extracting("id").containsExactly(coffeeMoimChamyo.getId());
	}
}
