package mouda.backend.moim.presentation.request.moim;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MoimEditRequest(
	@NotNull @Positive
	Long moimId,

	@NotBlank
	String title,

	LocalDate date,

	LocalTime time,

	String place,

	@NotNull
	Integer maxPeople,

	String description
) {
}
