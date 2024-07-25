package mouda.backend.moim.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.moim.domain.Moim;

public record MoimCreateRequest(
	@NotBlank
	String title,

	@NotNull
	LocalDate date,

	@NotNull
	LocalTime time,

	@NotBlank
	String place,

	@NotNull
	Integer maxPeople,

	@NotBlank
	String authorNickname,

	String description
) {

	public Moim toEntity() {
		return Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build();
	}
}
