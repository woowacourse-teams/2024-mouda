package mouda.backend.moim.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.moim.exception.MoimException;

class MoimTest {
	private String title;
	private LocalDate date;
	private LocalTime time;
	private String place;
	private int maxPeople;
	private String authorNickname;
	private String description;

	@BeforeEach
	void setUp() {
		title = "이번 주에 축구하실 분 구함";
		date = LocalDate.now().plusDays(1);
		time = LocalTime.now().plusHours(1);
		place = "서울시 동작구 강원대로 10길 5";
		maxPeople = 11;
		authorNickname = "안나";
		description = "이번 주 금요일에 퇴근하고 축구하실 분 계신가요? 끝나고 치맥도 할 생각입니다.";
	}

	@DisplayName("모임 객체를 정상적으로 생성한다.")
	@Test
	void createMoim() {
		Assertions.assertDoesNotThrow(() -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("제목 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTitleIsTooLong() {
		String longTitle = "a".repeat(31);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(longTitle)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("제목이 빈 문자열이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTitleDoesNotExists() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title("")
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("날짜가 null이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenDateIsNull() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(null)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("시간이 null이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTimeIsNull() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(null)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("모임 날짜가 현재보다 과거이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenDateIsPast() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(LocalDate.now().minusDays(1))
			.time(LocalTime.now().plusHours(1))
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("모임 시간이 현재보다 과거이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenTimeIsPast() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(LocalDate.now())
			.time(LocalTime.now().minusHours(1))
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("장소가 빈 문자열이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenPlaceIsBlank() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(LocalDate.now())
			.time(LocalTime.now().minusHours(1))
			.place("")
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("장소 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenPlaceIsTooLong() {
		String longPlace = "a".repeat(101);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(longPlace)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("모임 최대 인원이 0보다 작으면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenMaxPeopleIsTooSmall() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(-1)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("모임 최대 인원이 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenMaxPeopleIsTooMany() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(100)
			.authorNickname(authorNickname)
			.description(description)
			.build());
	}

	@DisplayName("작성자 닉네임이 빈 문자열이면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenAuthorNicknameIsBlank() {
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname("")
			.description(description)
			.build());
	}

	@DisplayName("작성자 닉네임 길이가 제한을 초과하면 모임 객체 생성에 실패한다.")
	@Test
	void failToCreateMoimWhenAuthorNicknameIsTooLong() {
		String longNickname = "a".repeat(11);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(longNickname)
			.description(description)
			.build());
	}

	@DisplayName("설명이 null이면 모임 객체 생성에 성공한다.")
	@Test
	void createMoimWhenDescriptionIsNull() {
		assertDoesNotThrow(() -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(null)
			.build());
	}

	@DisplayName("설명의 길이가 길면 모임 객체 생성에 성공한다.")
	@Test
	void createMoimWhenDescriptionIsTooLong() {
		String longDescription = "a".repeat(1001);
		assertThrows(MoimException.class, () -> Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(longDescription)
			.build());
	}
}
