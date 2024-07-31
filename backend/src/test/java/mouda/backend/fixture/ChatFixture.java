package mouda.backend.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.chat.domain.Chat;

public class ChatFixture {

	public static Chat getChatWithHogeeAtBasketballMoim() {
		return Chat.builder()
			.time(LocalTime.now())
			.date(LocalDate.now())
			.content("ㅎㅇㅎㅇ")
			.member(MemberFixture.getHogee())
			.moim(MoimFixture.getBasketballMoim())
			.build();
	}

	public static Chat getChatWithAnnaAtCoffeeMoim() {
		return Chat.builder()
			.time(LocalTime.now())
			.date(LocalDate.now())
			.content("반갑읍니다")
			.member(MemberFixture.getAnna())
			.moim(MoimFixture.getCoffeeMoim())
			.build();
	}

	public static Chat getChatWithHogeeAtCoffeeMoim() {
		return Chat.builder()
			.time(LocalTime.now())
			.date(LocalDate.now())
			.content("반갑읍니다")
			.member(MemberFixture.getHogee())
			.moim(MoimFixture.getCoffeeMoim())
			.build();
	}

	public static Chat getChatWithTebahAtSoccerMoim() {
		return Chat.builder()
			.time(LocalTime.now())
			.date(LocalDate.now())
			.content("ㅎㅇㅎㅇ")
			.member(MemberFixture.getTebah())
			.moim(MoimFixture.getSoccerMoim())
			.build();
	}
}
