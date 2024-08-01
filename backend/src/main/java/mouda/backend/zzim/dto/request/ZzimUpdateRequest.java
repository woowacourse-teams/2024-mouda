package mouda.backend.zzim.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ZzimUpdateRequest(
	@NotNull @Positive
	Long moimId
) {
}
