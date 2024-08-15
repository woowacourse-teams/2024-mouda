package mouda.backend.darakbang.dto.response;

import lombok.Builder;

@Builder
public record CodeValidationResponse(
	boolean isValid
) {

	public static CodeValidationResponse toResponse(boolean isValid) {
		return CodeValidationResponse.builder()
			.isValid(isValid)
			.build();
	}
}
