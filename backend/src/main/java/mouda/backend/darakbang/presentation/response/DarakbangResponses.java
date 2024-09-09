package mouda.backend.darakbang.presentation.response;

import java.util.List;

import lombok.Builder;
import mouda.backend.darakbang.domain.Darakbangs;

@Builder
public record DarakbangResponses(
	List<DarakbangResponse> darakbangResponses
) {

	public static DarakbangResponses toResponse(Darakbangs darakbangs) {
		List<DarakbangResponse> responses = darakbangs.getDarakbangs()
			.stream()
			.map(darakbang -> DarakbangResponse.builder()
				.darakbangId(darakbang.getId())
				.name(darakbang.getName())
				.build())
			.toList();
		return new DarakbangResponses(responses);
	}
}
