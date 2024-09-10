package mouda.backend.please.presentation.response;

import java.util.List;

public record PleaseFindAllResponses(
	List<PleaseFindAllResponse> pleases
) {

	public static PleaseFindAllResponses toResponse(List<PleaseFindAllResponse> pleaseFindAllResponses) {
		return new PleaseFindAllResponses(pleaseFindAllResponses);
	}
}
