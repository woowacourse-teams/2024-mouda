package mouda.backend.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.moim.domain.Moim;

public class MoimFixture {

	public static Moim getSoccerMoim() {
		return Moim.builder()
			.title("풋살할 사람?")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("잠실 종합운동장")
			.description("잘하는 사람만 와라")
			.maxPeople(22)
			.build();
	}

	public static Moim getBasketballMoim() {
		return Moim.builder()
			.title("농구할 사람?")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("테바 집")
			.description("파주로 와라")
			.maxPeople(10)
			.build();
	}

	public static Moim getCoffeeMoim() {
		return Moim.builder()
			.title("커피 마실 사람?")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("안나 집")
			.description("커피 머신 들고와라")
			.maxPeople(100)
			.build();
	}
}
