package mouda.backend.moim.presentation.request.chat;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatType;
import mouda.backend.moim.domain.Moim;

public record ChatCreateRequest(
	@NotNull
	Long moimId,

	@NotBlank
	String content
) {
	public Chat toEntity(Moim moim, DarakbangMember darakbangMember) {
		return Chat.builder()
			.content(content)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.moim(moim)
			.chatType(ChatType.BASIC)
			.build();
	}
}
