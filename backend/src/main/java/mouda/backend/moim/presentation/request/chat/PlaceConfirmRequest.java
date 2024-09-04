package mouda.backend.moim.presentation.request.chat;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatType;
import mouda.backend.moim.domain.Moim;

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
