package mouda.backend.darakbang.dto.response;

import lombok.Builder;
import mouda.backend.darakbang.domain.Darakbang;

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
