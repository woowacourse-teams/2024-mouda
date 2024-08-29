package mouda.backend.chamyo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "모임 참여 요청", description = "모임에 참여할 때 사용")
public record MoimChamyoRequest(
	@NotNull
	@Positive
	@Schema(description = "참여하고자 하는 모임의 ID", minimum = "1", example = "1")
	Long moimId
) {
}

