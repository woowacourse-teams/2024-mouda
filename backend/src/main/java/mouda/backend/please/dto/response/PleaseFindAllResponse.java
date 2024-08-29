package mouda.backend.please.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.please.domain.Please;

@Builder
@Schema(name = "해주세요 조회 응답", description = "해주세요를 조회할 때 사용")
public record PleaseFindAllResponse(
	@Schema(description = "해주세요 ID", minimum = "1", example = "1")
	long pleaseId,
	@Schema(description = "해주세요 제목", example = "농구 모임")
	String title,
	@Schema(description = "해주세요 상세", example = "농구 모임 만들어주세요~")
	String description,
	@Schema(description = "관심 여부", example = "true")
	boolean isInterested,
	@Schema(description = "총 관심 수", example = "10")
	long interestCount
) {
	public static PleaseFindAllResponse toResponse(Please please, boolean isInterested, long interestCount) {
		return PleaseFindAllResponse.builder()
			.pleaseId(please.getId())
			.title(please.getTitle())
			.description(please.getDescription())
			.isInterested(isInterested)
			.interestCount(interestCount)
			.build();
	}
}
