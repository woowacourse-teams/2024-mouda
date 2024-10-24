package mouda.backend.moim.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.notificiation.ChamyoRecipientFinder;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
public class ChamyoAsyncTest extends DarakbangSetUp {

	@Autowired
	private ChamyoService chamyoService;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@MockBean
	private ChamyoRecipientFinder chamyoRecipientFinder;

	private Moim moim;

	@BeforeEach
	void init() {
		moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		chamyoRepository.save(Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build());
	}

	@DisplayName("회원 참여시 알림 전송 과정에서 예외가 발생해도 참여는 정상적으로 처리된다.")
	@Test
	void asyncWhenChamyoCreate() {
		// when
		when(chamyoRecipientFinder.getChamyoNotificationRecipients(any(Moim.class), any(DarakbangMember.class)))
			.thenThrow(new RuntimeException("삐용12"));

		chamyoService.chamyoMoim(darakbang.getId(), moim.getId(), darakbangHogee);

		// then
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moim.getId());
		assertThat(chamyos).hasSize(2);
		assertThat(chamyos).extracting(chamyo -> chamyo.getDarakbangMember().getNickname())
			.contains(darakbangHogee.getNickname());
	}

	@DisplayName("회원 참여 취소시 알림 전송 과정에서 예외가 발생해도 취소는 정상적으로 처리된다.")
	@Test
	void asyncWhenChamyoCancel() {
		// when
		when(chamyoRecipientFinder.getChamyoNotificationRecipients(any(Moim.class), any(DarakbangMember.class)))
			.thenThrow(new RuntimeException("삐용12"));

		chamyoService.chamyoMoim(darakbang.getId(), moim.getId(), darakbangHogee);
		chamyoService.cancelChamyo(darakbang.getId(), moim.getId(), darakbangHogee);

		// then
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moim.getId());
		assertThat(chamyos).hasSize(1);
		assertThat(chamyos).extracting(chamyo -> chamyo.getDarakbangMember().getNickname())
			.doesNotContain(darakbangHogee.getNickname());
	}
}
