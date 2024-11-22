package mouda.backend.common.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.moim.domain.Moim;

public class MoimFixture {

	public static Moim getSoccerMoim(long darakbangId) {
		return Moim.builder()
			.title("풋살할 사람?")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("잠실 종합운동장")
			.description("잘하는 사람만 와라")
			.maxPeople(22)
			.darakbangId(darakbangId)
			.build();
	}

	public static Moim getBasketballMoim(long darakbangId) {
		return Moim.builder()
			.title("농구할 사람?")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("테바 집")
			.description("파주로 와라")
			.maxPeople(10)
			.darakbangId(darakbangId)
			.build();
	}

	public static Moim getAloneMoim(long darakbangId) {
		return Moim.builder()
			.title("혼자 놀기")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("상돌 집")
			.description("혼자가 좋아")
			.maxPeople(1)
			.darakbangId(darakbangId)
			.build();
	}

	public static Moim getCoffeeMoim(long darakbangId) {
		return Moim.builder()
			.title("커피 마실 사람?")
			.time(LocalTime.now().plusHours(1))
			.date(LocalDate.now().plusDays(1))
			.place("안나 집")
			.description("커피 머신 들고와라")
			.maxPeople(60)
			.darakbangId(darakbangId)
			.build();
	}
}
