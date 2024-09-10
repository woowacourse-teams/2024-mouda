package mouda.backend.darakbang.presentation.response;

import lombok.Builder;

@Builder
public record DarakbangResponse(
	long darakbangId,
	String name
) {

	public static DarakbangResponse toResponse(Long darakbangId, String name) {
		return DarakbangResponse.builder()
			.darakbangId(darakbangId)
			.name(name)
			.build();
	}
}
