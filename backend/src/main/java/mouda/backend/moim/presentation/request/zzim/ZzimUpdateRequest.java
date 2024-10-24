package mouda.backend.moim.presentation.request.zzim;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ZzimUpdateRequest(
	@NotNull @Positive
	Long moimId
) {
}
