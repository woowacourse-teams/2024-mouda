package mouda.backend.please.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "모든 해주세요 조회 응답", description = "모든 해주세요를 조회할 때 사용")
public record PleaseFindAllResponses(
	@Schema(description = "해주세요 목록")
	List<PleaseFindAllResponse> pleases
) {
}
