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
class ChamyoRecipientFinderTest extends DarakbangSetUp {

	@Autowired
	ChamyoRecipientFinder chamyoRecipientFinder;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	ChamyoRepository chamyoRepository;

	@DisplayName("모임 참여/참여 취소는 참여자/참여취소자를 제외한 모든 인원에게 전송한다.")
	@Test
	void getChamyoNotificationRecipients() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);
		Chamyo chamyoWithMoimerAnna = Chamyo.builder()
			.moim(savedMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build();
		chamyoRepository.save(chamyoWithMoimerAnna);

		// when
		List<Recipient> recipients = chamyoRecipientFinder.getChamyoNotificationRecipients(savedMoim.getId(), darakbangAnna);

		//then
		assertThat(recipients).hasSize(1);
	}
}
