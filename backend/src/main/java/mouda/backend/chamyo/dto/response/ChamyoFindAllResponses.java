package mouda.backend.chamyo.dto.response;

import java.util.List;

public record ChamyoFindAllResponses(
	List<ChamyoFindAllResponse> chamyos
) {
}
