package mouda.backend.moim.implement.writer;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
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
class ChamyoWriterTest extends DarakbangSetUp {

	@Autowired
	private ChamyoWriter chamyoWriter;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@DisplayName("모이머로 참여한다.")
	@Test
	void saveAsMoimer() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		Chamyo chamyo = chamyoWriter.saveAsMoimer(moim, darakbangHogee);

		assertThat(chamyo.getMoimRole()).isEqualTo(MoimRole.MOIMER);
	}

	@DisplayName("모이미로 참여한다.")
	@Test
	void saveAsMoimee() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		Chamyo chamyo = chamyoWriter.saveAsMoimee(moim, darakbangHogee);

		assertThat(chamyo.getMoimRole()).isEqualTo(MoimRole.MOIMEE);
	}

	@DisplayName("참여 인원이 만원인 경우 예외가 발생한다.")
	@Test
	void failToChamyoWhenMoimIsFull() {
		Moim moim = moimRepository.save(MoimFixture.getAloneMoim(darakbang.getId()));
		chamyoRepository.save(new Chamyo(moim, darakbangAnna, MoimRole.MOIMER));

		assertThatThrownBy(() -> chamyoWriter.saveAsMoimee(moim, darakbangHogee))
			.isInstanceOf(ChamyoException.class);
	}

	@DisplayName("참여가 취소된 경우 예외가 발생한다.")
	@Test
	void failToChamyoWhenMoimIsCancel() {
		Moim moim = moimRepository.save(MoimFixture.getAloneMoim(darakbang.getId()));
		moim.cancel();

		assertThatThrownBy(() -> chamyoWriter.saveAsMoimee(moim, darakbangHogee))
			.isInstanceOf(ChamyoException.class);
	}

	@DisplayName("참여가 모집 완료된 경우 예외가 발생한다.")
	@Test
	void failToChamyoWhenMoimIsCompleted() {
		Moim moim = moimRepository.save(MoimFixture.getAloneMoim(darakbang.getId()));
		moim.complete();

		assertThatThrownBy(() -> chamyoWriter.saveAsMoimee(moim, darakbangHogee))
			.isInstanceOf(ChamyoException.class);
	}

	@DisplayName("이미 참여한 모임인 경우 예외가 발생한다.")
	@Test
	void failToChamyoWhenAlreadyChamyo() {
		Moim moim = moimRepository.save(MoimFixture.getAloneMoim(darakbang.getId()));
		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		assertThatThrownBy(() -> chamyoWriter.saveAsMoimer(moim, darakbangHogee))
			.isInstanceOf(ChamyoException.class);
	}

	@DisplayName("참여한 모임을 취소한다.")
	@Test
	void delete() {
		Moim moim = moimRepository.save(MoimFixture.getAloneMoim(darakbang.getId()));
		Chamyo chamyo = chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE));

		chamyoWriter.delete(chamyo);

		Optional<Chamyo> optionalChamyo = chamyoRepository.findByMoimIdAndDarakbangMemberId(moim.getId(),
			darakbangHogee.getId());
		assertThat(optionalChamyo).isEmpty();
	}
}
