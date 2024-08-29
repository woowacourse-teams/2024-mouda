package mouda.backend.moim.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "모든 모임 조회 응답", description = "모든 모임을 조회할 때 사용")
public record MoimFindAllResponses(
	@Schema(description = "모임 목록")
	List<MoimFindAllResponse> moims
) {
}
