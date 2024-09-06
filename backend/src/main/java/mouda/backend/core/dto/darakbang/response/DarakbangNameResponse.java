package mouda.backend.core.dto.darakbang.response;

import lombok.Builder;
import mouda.backend.core.domain.darakbang.Darakbang;

@Builder
public record DarakbangNameResponse(
	String name
) {

	public static DarakbangNameResponse toResponse(Darakbang darakbang) {
		return DarakbangNameResponse.builder()
			.name(darakbang.getName())
			.build();
	}
}
