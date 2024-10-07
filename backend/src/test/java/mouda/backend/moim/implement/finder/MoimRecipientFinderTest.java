package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.notification.domain.Recipient;

@SpringBootTest
class MoimRecipientFinderTest extends DarakbangSetUp {

	@Autowired
	MoimRecipientFinder moimRecipientFinder;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	ChamyoRepository chamyoRepository;

	@DisplayName("모임 생성 알림은 다락방 참여 전원에게 알린다.")
	@Test
	void getMoimCreatedNotificationRecipients() {
		// given
		Long darakbangId = darakbang.getId();

		// when
		List<Recipient> recipients = moimRecipientFinder.getMoimCreatedNotificationRecipients(darakbangId, 123L);

		//then
		assertThat(recipients).hasSize(2);
	}

	@DisplayName("모임 상태 변화(모집마감, 모집재개, 모임정보변경, 모임장소/시간 확정)는 방장 제외 모임참여자 전원에게 알린다.")
	@Test
	void getMoimStatusChangedNotificationRecipients() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);
		Chamyo chamyoWithMoimerAnna = Chamyo.builder()
			.moim(savedMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build();

		Chamyo chamyoWithMoimeeHogee = Chamyo.builder()
			.moim(savedMoim)
			.darakbangMember(darakbangHogee)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(chamyoWithMoimerAnna);
		chamyoRepository.save(chamyoWithMoimeeHogee);

		// when
		List<Recipient> recipients = moimRecipientFinder.getMoimStatusChangedNotificationRecipients(savedMoim.getId());

		//then
		assertThat(recipients).hasSize(1);
	}
}
