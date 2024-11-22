package mouda.backend.darakbang.presentation.response;

import lombok.Builder;
import mouda.backend.darakbang.domain.Darakbang;

@Builder
public record CodeValidationResponse(
	Long darakbangId,
	String name
) {

	public static CodeValidationResponse toResponse(Darakbang darakbang) {
		return CodeValidationResponse.builder()
			.darakbangId(darakbang.getId())
			.name(darakbang.getName())
			.build();
	}
}
