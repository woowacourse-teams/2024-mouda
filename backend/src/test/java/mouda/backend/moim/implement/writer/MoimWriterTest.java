package mouda.backend.moim.implement.writer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class MoimWriterTest extends DarakbangSetUp {

	@Autowired
	private MoimWriter moimWriter;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private MoimRepository moimRepository;

	@DisplayName("모집 인원이 다 차면 모집을 완료한다.")
	@Test
	void updateMoimStatusIfFull() {
		Moim moim = moimRepository.save(MoimFixture.getAloneMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMER);
		chamyoRepository.save(chamyo);

		moimWriter.updateMoimStatusIfFull(moim);

		assertThat(moim.getMoimStatus()).isEqualTo(MoimStatus.COMPLETED);
	}

	@DisplayName("모이머가 장소를 변경하면 성공한다.")
	@Test
	void confirmPlace() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMER);
		chamyoRepository.save(chamyo);

		String place = "호기네 카페";
		moimWriter.confirmPlace(moim, darakbangHogee, place);

		assertThat(moim.getPlace()).isEqualTo(place);
	}

	@DisplayName("모이머가 아닌 사람이 장소를 변경하면 실패한다.")
	@Test
	void failToConfirmPlaceWithoutMoimer() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE);
		chamyoRepository.save(chamyo);

		assertThatThrownBy(() -> moimWriter.confirmPlace(moim, darakbangHogee, "호기네 카페"))
			.isInstanceOf(ChamyoException.class);
	}

	@DisplayName("모이머가 일정을 변경하면 성공한다.")
	@Test
	void confirmDateTime() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMER);
		chamyoRepository.save(chamyo);

		LocalDate nowDate = LocalDate.now().plusDays(1);
		LocalTime nowTime = LocalTime.now().plusHours(1);
		moimWriter.confirmDateTime(moim, darakbangHogee, nowDate, nowTime);

		assertAll(
			() -> assertThat(moim.getDate()).isEqualTo(nowDate),
			() -> assertThat(moim.getTime()).isEqualTo(nowTime)
		);
	}

	@DisplayName("모이머가 아닌 사람이 일정을 변경하면 실패한다.")
	@Test
	void failToConfirmDateTimeWithoutMoimer() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE);
		chamyoRepository.save(chamyo);

		LocalDate nowDate = LocalDate.now().plusDays(1);
		LocalTime nowTime = LocalTime.now().plusHours(1);

		assertThatThrownBy(() -> moimWriter.confirmDateTime(moim, darakbangHogee, nowDate, nowTime))
			.isInstanceOf(ChamyoException.class);
	}

	@DisplayName("모이머는 채팅을 열 수 있다.")
	@Test
	void openChatRoom() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMER);
		chamyoRepository.save(chamyo);

		moimWriter.openChatByMoimer(moim, darakbangHogee);

		assertThat(moim.isChatOpened()).isTrue();
	}

	@DisplayName("모이머가 아니라면 채팅을 열 수 없다.")
	@Test
	void failToOpenChatRoomWithoutMoimer() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Chamyo chamyo = new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE);
		chamyoRepository.save(chamyo);

		assertThatThrownBy(() -> moimWriter.openChatByMoimer(moim, darakbangHogee))
			.isInstanceOf(ChamyoException.class);
	}
}
