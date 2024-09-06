package mouda.backend.core.dto.moim.response.moim;

import java.util.List;

public record MoimFindAllResponses(
	List<MoimFindAllResponse> moims
) {
}
