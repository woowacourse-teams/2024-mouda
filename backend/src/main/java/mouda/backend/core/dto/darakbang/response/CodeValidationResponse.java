package mouda.backend.core.dto.darakbang.response;

import lombok.Builder;
import mouda.backend.core.domain.darakbang.Darakbang;

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
