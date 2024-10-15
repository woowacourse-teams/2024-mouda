package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.writer.ChamyoWriter;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.notification.domain.Recipient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatRecipientFinderTest extends DarakbangSetUp {

	@Autowired
	private ChatRecipientFinder chatRecipientFinder;

	@Autowired
	private ChamyoWriter chamyoWriter;

	@Autowired
	private MoimWriter moimWriter;

	@DisplayName("채팅 알림은 메시지를 보낸 사람을 제외한 모든 참여자를 대상으로 한다.")
	@Test
	void getChatNotificationRecipients() {
		// given
		Moim moim = moimWriter.save(MoimFixture.getCoffeeMoim(darakbang.getId()), darakbangAnna);
		chamyoWriter.saveAsMoimee(moim, darakbangHogee);

		// when
		List<Recipient> result = chatRecipientFinder.getChatNotificationRecipients(moim.getId(),
			darakbangAnna);

		// then
		assertThat(result).hasSize(1);
		assertThat(result).extracting("memberId").containsExactly(darakbangHogee.getMemberId());
	}
}