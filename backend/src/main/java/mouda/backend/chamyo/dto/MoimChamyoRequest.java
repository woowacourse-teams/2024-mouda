package mouda.backend.chamyo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MoimChamyoRequest(
	@NotNull @Positive
	Long moimId
) {
}
