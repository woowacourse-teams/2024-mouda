package mouda.backend.moim.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MoimJoinRequest(
	@NotNull
	@Positive
	Long moimId,

	@NotBlank
	String nickname
) {
}
