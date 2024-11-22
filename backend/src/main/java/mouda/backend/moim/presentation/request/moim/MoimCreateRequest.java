package mouda.backend.moim.presentation.request.moim;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.moim.domain.Moim;

public record MoimCreateRequest(
	@NotBlank
	String title,

	LocalDate date,

	LocalTime time,

	String place,

	@NotNull
	Integer maxPeople,

	String description
) {

	public Moim toEntity(Long darakbangId) {
		return Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.description(description)
			.darakbangId(darakbangId)
			.build();
	}
}
