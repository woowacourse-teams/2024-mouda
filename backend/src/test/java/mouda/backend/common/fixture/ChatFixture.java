package mouda.backend.common.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Chat;
import mouda.backend.core.domain.moim.ChatType;
import mouda.backend.core.domain.moim.Moim;

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
