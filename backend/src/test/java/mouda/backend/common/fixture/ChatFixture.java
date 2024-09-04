package mouda.backend.common.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatType;
import mouda.backend.moim.domain.Moim;

public class ChatFixture {

	public static Chat getChatWithMemberAtMoim(DarakbangMember darakbangMember, Moim moim) {
		return Chat.builder()
			.time(LocalTime.now())
			.date(LocalDate.now())
			.content("안녕하쎄요")
			.darakbangMember(darakbangMember)
			.moim(moim)
			.chatType(ChatType.BASIC)
			.build();
	}
}
