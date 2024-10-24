package mouda.backend.moim.presentation.request.chamyo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChamyoCancelRequest(
	@NotNull @Positive
	Long moimId
) {
}
