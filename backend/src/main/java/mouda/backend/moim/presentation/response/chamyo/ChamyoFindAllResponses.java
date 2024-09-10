package mouda.backend.moim.presentation.response.chamyo;

import java.util.List;

import mouda.backend.moim.domain.Chamyo;

public record ChamyoFindAllResponses(
	List<ChamyoFindAllResponse> chamyos
) {

	public static ChamyoFindAllResponses toResponse(List<Chamyo> chamyos) {
		List<ChamyoFindAllResponse> responses = chamyos.stream()
			.map(ChamyoFindAllResponse::toResponse)
			.toList();

		return new ChamyoFindAllResponses(responses);
	}
}
