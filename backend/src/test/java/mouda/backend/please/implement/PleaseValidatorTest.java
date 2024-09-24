package mouda.backend.please.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.PleaseFixture;
import mouda.backend.please.domain.Please;
import mouda.backend.please.exception.PleaseException;

@SpringBootTest
class PleaseValidatorTest extends DarakbangSetUp {

	@Autowired
	private PleaseValidator pleaseValidator;

	@Autowired
	private PleaseWriter pleaseWriter;

	@DisplayName("해주세요는 하나의 다락방에만 종속된다. 아니면 예외가 발생한다.")
	@Test
	void validateNotInDarakbangTest() {
		Please savedPlease = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());

		assertThatThrownBy(() -> pleaseValidator.validate(savedPlease, 2L, darakbangHogee))
			.isInstanceOf(PleaseException.class)
			.hasMessage("다락방에 존재하는 해주세요가 아닙니다.");
	}

	@DisplayName("해주세요 삭제는 작성자만 할 수 있다.")
	@Test
	void validateAuthorizeTest() {
		Please savedPlease = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());

		assertThatThrownBy(() -> pleaseValidator.validate(savedPlease, 1L, darakbangAnna))
			.isInstanceOf(PleaseException.class)
			.hasMessage("삭제 권한이 없습니다.");
	}
}
