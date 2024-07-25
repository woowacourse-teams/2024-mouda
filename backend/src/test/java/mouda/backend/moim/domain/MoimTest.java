package mouda.backend.moim.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.moim.exception.MoimException;

class MoimTest {

	private static final String TITLE = "이번 주에 축구하실 분 구함";
	private static final LocalDate DATE = LocalDate.now().plusDays(1);
	private static final LocalTime TIME = LocalTime.now().plusHours(1);
	private static final String PLACE = "서울시 동작구 강원대로 10길 5";
	private static final int MAX_PEOPLE = 11;
	private static final String AUTHOR_NICKNAME = "안나";
	private static final String DESCRIPTION = "이번 주 금요일에 퇴근하고 축구하실 분 계신가요? 끝나고 치맥도 할 생각입니다.";

	@DisplayName("모임 객체를 정상적으로 생성한다.")
	@Test
	void createMoim() {
		Assertions.assertDoesNotThrow(() -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname(AUTHOR_NICKNAME)
			.description(DESCRIPTION)
			.build());
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
			.authorNickname(AUTHOR_NICKNAME)
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
			.authorNickname(AUTHOR_NICKNAME)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("날짜가 null이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenDateIsNull() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(null)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname(AUTHOR_NICKNAME)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("시간이 null이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTimeIsNull() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(null)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname(AUTHOR_NICKNAME)
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
			.authorNickname(AUTHOR_NICKNAME)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("모임 시간이 현재보다 과거이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTimeIsPast() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(LocalDate.now())
			.time(LocalTime.now().minusHours(1))
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname(AUTHOR_NICKNAME)
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
			.authorNickname(AUTHOR_NICKNAME)
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
			.authorNickname(AUTHOR_NICKNAME)
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
			.authorNickname(AUTHOR_NICKNAME)
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
			.authorNickname(AUTHOR_NICKNAME)
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("작성자 닉네임이 빈 문자열이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenAuthorNicknameIsBlank() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname("")
			.description(DESCRIPTION)
			.build());
	}

	@DisplayName("작성자 닉네임 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenAuthorNicknameIsTooLong() {
		String longNickname = "a".repeat(11);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname(longNickname)
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
			.authorNickname(AUTHOR_NICKNAME)
			.description(null)
			.build());
	}

	@DisplayName("설명의 길이가 길면 모임 객체 생성에 성공한다.")
	@Test
	void createMoimWhenDescriptionIsTooLong() {
		String longDescription = "a".repeat(1001);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(TITLE)
			.date(DATE)
			.time(TIME)
			.place(PLACE)
			.maxPeople(MAX_PEOPLE)
			.authorNickname(AUTHOR_NICKNAME)
			.description(longDescription)
			.build());
	}
}
