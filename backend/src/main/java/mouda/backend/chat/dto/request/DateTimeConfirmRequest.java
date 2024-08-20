package mouda.backend.chat.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatType;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;

public record DateTimeConfirmRequest(
	@NotNull
	Long moimId,

	@NotNull
	LocalDate date,

	@NotNull
	LocalTime time
) {
	public Chat toEntity(Moim moim, DarakbangMember darakbangMember) {
		return Chat.builder()
			.content(date.toString() + " " + time.toString())
			.moim(moim)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.DATETIME)
			.build();
	}
}
