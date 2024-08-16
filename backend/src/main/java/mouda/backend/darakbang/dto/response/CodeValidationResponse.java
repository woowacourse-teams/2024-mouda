package mouda.backend.darakbang.dto.response;

import lombok.Builder;

@Builder
public record CodeValidationResponse(
	String name
) {

	public static CodeValidationResponse toResponse(String name) {
		return CodeValidationResponse.builder()
			.name(name)
			.build();
	}
}
