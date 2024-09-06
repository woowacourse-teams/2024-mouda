package mouda.backend.core.dto.moim.request.zzim;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ZzimUpdateRequest(
	@NotNull @Positive
	Long moimId
) {
}
