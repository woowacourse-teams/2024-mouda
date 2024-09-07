package mouda.backend.moim.presentation.response.moim;

import java.util.List;

public record MoimFindAllResponses(
	List<MoimFindAllResponse> moims
) {
}
