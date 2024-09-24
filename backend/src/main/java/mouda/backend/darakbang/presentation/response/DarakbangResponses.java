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
			.map(darakbang -> DarakbangResponse.toResponse(
				darakbang.getId(), darakbang.getName()))
			.toList();
		return new DarakbangResponses(responses);
	}
}
