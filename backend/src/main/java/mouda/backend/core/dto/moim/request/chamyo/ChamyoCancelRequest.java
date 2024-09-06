package mouda.backend.core.dto.moim.request.chamyo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChamyoCancelRequest(
	@NotNull @Positive
	Long moimId
) {
}
