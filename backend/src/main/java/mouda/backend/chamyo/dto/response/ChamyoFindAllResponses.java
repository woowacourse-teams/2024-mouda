package mouda.backend.chamyo.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "모든 참여자 조회 응답", description = "모임의 모든 참여자를 조회할 때 사용")
public record ChamyoFindAllResponses(
	@Schema(description = "참여자 목록")
	List<ChamyoFindAllResponse> chamyos
) {
}
