package mouda.backend.darakbang.dto.response;

import lombok.Builder;
import mouda.backend.darakbang.domain.Darakbang;

@Builder
public record DarakbangResponse(
	long darakbangId,
	String name
) {
	
	public static DarakbangResponse toResponse(Darakbang darakbang) {
		return DarakbangResponse.builder()
			.darakbangId(darakbang.getId())
			.name(darakbang.getName())
			.build();
	}
}
