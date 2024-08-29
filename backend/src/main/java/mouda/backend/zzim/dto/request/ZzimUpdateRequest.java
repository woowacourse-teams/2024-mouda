package mouda.backend.zzim.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "찜 상태 변경 요청", description = "찜 상태를 변경할 때 사용")
public record ZzimUpdateRequest(
	@NotNull
	@Positive
	@Schema(description = "찜 상태를 변경하고자 하는 모임의 ID", minimum = "1", example = "1")
	Long moimId
) {
}
