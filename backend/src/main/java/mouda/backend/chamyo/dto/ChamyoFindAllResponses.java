package mouda.backend.chamyo.dto;

import java.util.List;

public record ChamyoFindAllResponses(
	List<ChamyoFindAllResponse> chamyos
) {
}
