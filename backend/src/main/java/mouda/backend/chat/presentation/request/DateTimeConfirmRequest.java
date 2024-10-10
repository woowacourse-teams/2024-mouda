package mouda.backend.chat.presentation.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.ChatType;

public record DateTimeConfirmRequest(
	@NotNull
	LocalDate date,

	@NotNull
	LocalTime time
) {
	public ChatEntity toEntity(long chatRoomId, DarakbangMember darakbangMember) {
		return ChatEntity.builder()
			.content(date.toString() + " " + time.toString())
			.chatRoomId(chatRoomId)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.DATETIME)
			.build();
	}
}
