package mouda.backend.moim.dto.response;

import java.util.List;

public record MoimFindAllResponses(
	List<MoimFindAllResponse> moims
) {
}
