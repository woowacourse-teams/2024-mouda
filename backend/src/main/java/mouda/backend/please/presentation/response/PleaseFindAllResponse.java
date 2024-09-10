package mouda.backend.please.presentation.response;

import lombok.Builder;
import mouda.backend.please.domain.PleaseWithInterest;

@Builder
public record PleaseFindAllResponse(
	long pleaseId,
	String title,
	String description,
	boolean isInterested,
	long interestCount
) {
	public static PleaseFindAllResponse toResponse(PleaseWithInterest pleaseWithInterest) {
		return PleaseFindAllResponse.builder()
			.pleaseId(pleaseWithInterest.getPlease().getId())
			.title(pleaseWithInterest.getPlease().getTitle())
			.description(pleaseWithInterest.getPlease().getDescription())
			.isInterested(pleaseWithInterest.isInterested())
			.interestCount(pleaseWithInterest.getInterestCount())
			.build();
	}
}
