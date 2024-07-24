package mouda.backend.moim.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import mouda.backend.moim.domain.Moim;

public record MoimCreateRequest(
	@NotBlank
	@Size(max = 30)
	String title,

	@NotNull
	@FutureOrPresent
	LocalDate date,

	@NotNull
	LocalTime time,

	@NotBlank
	String place,

	@NotNull
	@Positive
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
