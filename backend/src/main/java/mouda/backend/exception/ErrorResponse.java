package mouda.backend.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "예외 응답", description = "예외 발생 시 응답에 사용")
public record ErrorResponse(
	@Schema(description = "예외 메시지", example = "모임이 존재하지 않아요!")
	String message
) {
}
