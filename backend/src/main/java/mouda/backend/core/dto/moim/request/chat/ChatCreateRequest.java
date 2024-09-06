package mouda.backend.core.dto.moim.request.chat;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Chat;
import mouda.backend.core.domain.moim.ChatType;
import mouda.backend.core.domain.moim.Moim;

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
