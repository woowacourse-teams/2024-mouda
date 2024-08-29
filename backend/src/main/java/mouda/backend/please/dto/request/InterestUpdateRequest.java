package mouda.backend.please.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "해주세요 관심 상태 변경 요청", description = "해주세요의 관심 상태를 변경할 때 사용")
public record InterestUpdateRequest(
	@Schema(description = "관심 상태를 변경하고자 하는 해주세요 ID", minimum = "1", example = "1")
	long pleaseId,
	@Schema(description = "변경하고자 하는 관심 상태", example = "true")
	boolean isInterested
) {
}
