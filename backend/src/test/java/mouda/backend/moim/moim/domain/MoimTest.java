package mouda.backend.moim.moim.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.MoimException;

class MoimTest extends DarakbangSetUp {

	private static final String TITLE = "이번 주에 축구하실 분 구함";
	private static final LocalDate DATE = LocalDate.now().plusDays(1);
	private static final LocalTime TIME = LocalTime.now().plusHours(1);
	private static final String PLACE = "서울시 동작구 강원대로 10길 5";
	private static final int MAX_PEOPLE = 11;
	private static final String DESCRIPTION = "이번 주 금요일에 퇴근하고 축구하실 분 계신가요? 끝나고 치맥도 할 생각입니다.";

	@DisplayName("모임 객체를 정상적으로 생성한다.")
	@Test
	void createMoim() {
		Assertions.assertDoesNotThrow(() -> MoimFixture.getBasketballMoim(darakbang.getId()));
	}

	@DisplayName("제목 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTitleIsTooLong() {
		String longTitle = "a".repeat(31);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(longTitle)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("제목이 빈 문자열이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTitleDoesNotExists() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title("")
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("모임 날짜가 현재보다 과거이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenDateIsPast() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(LocalDate.now().minusDays(1))
			.time(LocalTime.now().plusHours(1))
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("날짜는 같고, 시간이 현재보다 과거이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTimeIsPast() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(LocalDate.now())
			.time(LocalTime.now().minusHours(1))
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("장소가 빈 문자열이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenPlaceIsBlank() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(LocalDate.now())
			.time(LocalTime.now().minusHours(1))
			.place("")
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("장소 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenPlaceIsTooLong() {
		String longPlace = "a".repeat(101);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(longPlace)
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("모임 최대 인원이 0보다 작으면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenMaxPeopleIsTooSmall() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(-1)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("모임 최대 인원이 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenMaxPeopleIsTooMany() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(100)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("설명이 null이면 모임 객체 생성에 성공한다.")
	@Test
	void createMoimWhenDescriptionIsNull() {
		assertDoesNotThrow(() -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(null)
			.build());
	}

	@DisplayName("설명의 길이가 길면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenDescriptionIsTooLong() {
		String longDescription = "a".repeat(1001);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(longDescription)
			.build());
	}

	@Nested
	@DisplayName("모임 수정 테스트")
	class UpdateMoimTest {

		private Moim moim = Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.description(DESCRIPTION)
			.build();

		@DisplayName("날짜, 시간, 장소가 없어도 수정할 수 있다.")
		@Test
		void success() {
			assertDoesNotThrow(
				() -> moim.update(TITLE, null, null, null, MAX_PEOPLE, null, MAX_PEOPLE + 1));
		}

		@DisplayName("최대 길이를 초과하는 제목으로는 수정할 수 없다.")
		@Test
		void fail_whenTitleIsTooLong() {
			String longTitle = "a".repeat(31);
			assertThrows(MoimException.class,
				() -> moim.update(longTitle, DATE, TIME, PLACE, MAX_PEOPLE, DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("제목이 빈 문자열이면 수정할 수 없다.")
		@Test
		void fail_whenTitleDoesNotExists() {
			assertThrows(MoimException.class,
				() -> moim.update("", DATE, TIME, PLACE, MAX_PEOPLE, DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("모임 날짜가 현재보다 과거이면 수정할 수 없다.")
		@Test
		void fail_whenDateIsPast() {
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, LocalDate.now().minusDays(1), TIME, PLACE, MAX_PEOPLE, DESCRIPTION,
					MAX_PEOPLE + 1));
		}

		@DisplayName("날짜는 같고, 시간이 현재보다 과거이면 수정할 수 없다.")
		@Test
		void fail_whenTimeIsPast() {
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, LocalDate.now(), LocalTime.now().minusHours(1), PLACE, MAX_PEOPLE,
					DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("장소가 빈 문자열이면 수정할 수 없다.")
		@Test
		void fail_whenPlaceIsBlank() {
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, DATE, TIME, "", MAX_PEOPLE, DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("장소 길이가 제한을 초과하면 수정할 수 없다.")
		@Test
		void fail_whenPlaceIsTooLong() {
			String longPlace = "a".repeat(101);
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, DATE, TIME, longPlace, MAX_PEOPLE, DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("모임 최대 인원이 1보다 작으면 수정할 수 없다.")
		@Test
		void fail_whenMaxPeopleIsTooSmall() {
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, DATE, TIME, PLACE, 0, DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("모임 최대 인원이 제한을 초과하면 수정할 수 없다.")
		@Test
		void fail_whenMaxPeopleIsTooMany() {
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, DATE, TIME, PLACE, 100, DESCRIPTION, MAX_PEOPLE + 1));
		}

		@DisplayName("모임 최대 인원이 현재 참여 인원보다 적으면 수정할 수 없다.")
		@Test
		void fail_whenMaxPeopleIsLowerThanCurrentPeople() {
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, DATE, TIME, PLACE, MAX_PEOPLE - 1, DESCRIPTION, MAX_PEOPLE));
		}

		@DisplayName("설명의 길이가 길면 수정할 수 없다.")
		@Test
		void fail_whenDescriptionIsTooLong() {
			String longDescription = "a".repeat(1001);
			assertThrows(MoimException.class,
				() -> moim.update(TITLE, DATE, TIME, PLACE, MAX_PEOPLE, longDescription, MAX_PEOPLE + 1));
		}

		@DisplayName("모임 객체를 수정한다.")
		@Test
		void updateMoim() {
			String newTitle = "축구 모집합니다.";
			LocalDate newDate = LocalDate.now().plusDays(2);
			LocalTime newTime = LocalTime.now().plusHours(2);
			String newPlace = "서울시 강남구 강남대로 10길 5";
			int newMaxPeople = MAX_PEOPLE + 1;
			String newDescription = "축구하실 분 구합니다.";

			moim.update(newTitle, newDate, newTime, newPlace, newMaxPeople, newDescription, MAX_PEOPLE);

			assertEquals(newTitle, moim.getTitle());
			assertEquals(newDate, moim.getDate());
			assertEquals(newTime, moim.getTime());
			assertEquals(newPlace, moim.getPlace());
			assertEquals(newMaxPeople, moim.getMaxPeople());
			assertEquals(newDescription, moim.getDescription());
		}
	}
}
