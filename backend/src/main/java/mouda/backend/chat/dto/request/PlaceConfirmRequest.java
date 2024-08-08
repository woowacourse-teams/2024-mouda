package mouda.backend.chat.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatType;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;

public record PlaceConfirmRequest(
	@NotNull
	Long moimId,

	@NotBlank
	String place
) {
	public Chat toEntity(Moim moim, Member member) {
		return Chat.builder()
			.content(place)
			.moim(moim)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.member(member)
			.chatType(ChatType.PLACE)
			.build();
	}
}
