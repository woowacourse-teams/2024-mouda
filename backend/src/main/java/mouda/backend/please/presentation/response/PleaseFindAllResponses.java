package mouda.backend.please.presentation.response;

import java.util.List;

import mouda.backend.please.domain.PleaseWithInterests;

public record PleaseFindAllResponses(
	List<PleaseFindAllResponse> pleases
) {

	public static PleaseFindAllResponses toResponse(PleaseWithInterests pleaseWithInterests) {
		List<PleaseFindAllResponse> pleaseFindAllResponses = pleaseWithInterests.getPleaseWithInterests().stream()
			.map(PleaseFindAllResponse::toResponse)
			.toList();

		return new PleaseFindAllResponses(pleaseFindAllResponses);
	}
}
