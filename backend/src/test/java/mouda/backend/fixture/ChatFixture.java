package mouda.backend.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.chat.domain.Chat;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;

public class ChatFixture {

	public static Chat getChatWithMemberAtMoim(Member member, Moim moim) {
		return Chat.builder()
			.time(LocalTime.now())
			.date(LocalDate.now())
			.content("안녕하쎄요")
			.member(member)
			.moim(moim)
			.build();
	}
}
