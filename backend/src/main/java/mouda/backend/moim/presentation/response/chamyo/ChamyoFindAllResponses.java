package mouda.backend.moim.presentation.response.chamyo;

import java.util.List;

public record ChamyoFindAllResponses(
	List<ChamyoFindAllResponse> chamyos
) {
}
