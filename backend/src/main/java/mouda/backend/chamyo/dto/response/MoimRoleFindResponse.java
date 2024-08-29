package mouda.backend.chamyo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.chamyo.domain.MoimRole;

@Builder
@Schema(name = "모임 역할 조회 응답", description = "모임에 대한 사용자의 역할을 조회할 때 사용")
public record MoimRoleFindResponse(
	@Schema(description = "방장 / 참여자 / 미참여자 등 해당 모임에 대한 회원의 상태", example = "MOIMER")
	MoimRole role
) {
}
