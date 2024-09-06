package mouda.backend.core.dto.moim.request.chat;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Chat;
import mouda.backend.core.domain.moim.ChatType;
import mouda.backend.core.domain.moim.Moim;

public record PlaceConfirmRequest(
	@NotNull
	Long moimId,

	@NotBlank
	String place
) {
	public Chat toEntity(Moim moim, DarakbangMember darakbangMember) {
		return Chat.builder()
			.content(place)
			.moim(moim)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.PLACE)
			.build();
	}
}
