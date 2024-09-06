package mouda.backend.core.dto.moim.response.chamyo;

import java.util.List;

public record ChamyoFindAllResponses(
	List<ChamyoFindAllResponse> chamyos
) {
}
