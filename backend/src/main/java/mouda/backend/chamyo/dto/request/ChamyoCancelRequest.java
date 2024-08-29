package mouda.backend.chamyo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "참여 취소 요청", description = "모임 참여를 취소할 때 사용")
public record ChamyoCancelRequest(
	@NotNull
	@Positive
	@Schema(description = "취소하고자 하는 모임의 ID", minimum = "1", example = "1")
	Long moimId
) {
}
