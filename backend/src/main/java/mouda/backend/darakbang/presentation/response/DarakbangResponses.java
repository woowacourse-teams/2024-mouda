package mouda.backend.darakbang.presentation.response;

import java.util.List;

import lombok.Builder;

@Builder
public record DarakbangResponses(
	List<DarakbangResponse> darakbangResponses
) {
	
	public static DarakbangResponses toResponse(List<DarakbangResponse> responses) {
		return DarakbangResponses.builder()
			.darakbangResponses(responses)
			.build();
	}
}
