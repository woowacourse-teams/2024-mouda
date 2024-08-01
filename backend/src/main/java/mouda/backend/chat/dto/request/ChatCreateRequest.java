package mouda.backend.chat.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.chat.domain.Chat;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;

public record ChatCreateRequest(
	@NotNull
	Long moimId,
	
	@NotBlank
	String content
) {
	public Chat toEntity(Moim moim, Member member) {
		return Chat.builder()
			.content(content)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.member(member)
			.moim(moim)
			.build();
	}
}
