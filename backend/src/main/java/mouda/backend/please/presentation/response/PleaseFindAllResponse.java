package mouda.backend.please.presentation.response;

import lombok.Builder;
import mouda.backend.please.domain.Please;

@Builder
public record PleaseFindAllResponse(
	long pleaseId,
	String title,
	String description,
	boolean isInterested,
	long interestCount
) {
	public static PleaseFindAllResponse toResponse(Please please, boolean isInterested, long interestCount) {
		return PleaseFindAllResponse.builder()
			.pleaseId(please.getId())
			.title(please.getTitle())
			.description(please.getDescription())
			.isInterested(isInterested)
			.interestCount(interestCount)
			.build();
	}
}
