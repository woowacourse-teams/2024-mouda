package mouda.backend.darakbang.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.exception.DarakbangException;

@SpringBootTest
class DarakbangWriterTest {

	@Autowired
	private DarakbangWriter darakbangWriter;

	@DisplayName("다락방을 성공적으로 생성한다.")
	@Test
	void success() {
		Darakbang darakbang = darakbangWriter.save("우아한테크코스");

		assertThat(darakbang.getId()).isEqualTo(1L);
	}

	@DisplayName("다락방 이름이 존재하지 않으면 생성에 실패한다.")
	@NullAndEmptySource
	@ParameterizedTest
	void failToCreateDarakbangWithoutName(String name) {
		assertThatThrownBy(() -> darakbangWriter.save(name))
			.isInstanceOf(DarakbangException.class);
	}
}