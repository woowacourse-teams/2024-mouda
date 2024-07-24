package mouda.backend.moim.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.moim.exception.MoimException;

class MoimTest {

	@DisplayName("모임 객체를 정상적으로 생성한다.")
	@Test
	void createMoim() {
		Assertions.assertDoesNotThrow(() -> Moim.builder()
			.title("8 길이의 제목")
			.date(LocalDate.now().plusDays(10))
			.time(LocalTime.now().plusHours(1))
			.place("서울시 동작구 강원대로 10길 5")
			.maxPeople(10)
			.authorNickname("안나")
			.description("그다지 길지 않은 설명입니다.")
			.build());
	}

	@DisplayName("제목 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTitleIsTooLong() {
		String title = "a".repeat(31);
		Assertions.assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(LocalDate.now().plusDays(10))
			.time(LocalTime.now().plusHours(1))
			.place("서울시 동작구 강원대로 10길 5")
			.maxPeople(10)
			.authorNickname("안나")
			.description("그다지 길지 않은 설명입니다.")
			.build());
	}
}
