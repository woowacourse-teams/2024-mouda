package mouda.backend.zzim.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "찜 여부 확인 응답", description = "찜 여부를 확인할 때 사용")
public record ZzimCheckResponse(
	@Schema(description = "회원이 해당 모임을 찜 했는지 여부", example = "true")
	boolean isZzimed
) {
}
