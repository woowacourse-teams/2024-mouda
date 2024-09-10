package mouda.backend.darakbang.implement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;

@SpringBootTest
class InvitationCodeGeneratorTest {

	@SpyBean
	private InvitationCodeGenerator invitationCodeGenerator;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@DisplayName("7자리 랜덤코드를 생성한다.")
	@Test
	void success() {
		String invitationCode = invitationCodeGenerator.generate();

		assertThat(invitationCode).hasSize(7);
	}

	@DisplayName("초대코드가 중복되면 랜덤 코드 생성에 실패한다.")
	@Test
	void failToCreateDarakbangWithDuplicatedCode() {
		darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());

		doReturn("SOFABAC").when(invitationCodeGenerator).generate();

		assertThatThrownBy(() -> invitationCodeGenerator.generate())
			.isInstanceOf(DarakbangException.class);
	}
}
