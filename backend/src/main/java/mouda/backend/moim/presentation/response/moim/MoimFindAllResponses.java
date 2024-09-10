package mouda.backend.moim.presentation.response.moim;

import java.util.List;

import mouda.backend.moim.domain.MoimOverview;

public record MoimFindAllResponses(
	List<MoimFindAllResponse> moims
) {

	public static MoimFindAllResponses toResponse(List<MoimOverview> moimOverviews) {
		List<MoimFindAllResponse> responses = moimOverviews.stream()
			.map(MoimFindAllResponse::toResponse)
			.toList();

		return new MoimFindAllResponses(responses);
	}
}
