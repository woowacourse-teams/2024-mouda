package mouda.backend.moim.dto.request;

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

	@NotNull
	LocalDate date,

	@NotNull
	LocalTime time,

	@NotBlank
	String place,

	@NotNull
	Integer maxPeople,

	String description
) {
}
