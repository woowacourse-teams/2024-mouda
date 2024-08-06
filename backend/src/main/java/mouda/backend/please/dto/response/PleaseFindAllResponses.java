package mouda.backend.please.dto.response;

import java.util.List;

public record PleaseFindAllResponses(
	List<PleaseFindAllResponse> pleases
) {
}
