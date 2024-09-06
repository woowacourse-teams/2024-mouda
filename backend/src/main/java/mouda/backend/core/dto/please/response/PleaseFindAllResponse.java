package mouda.backend.core.dto.please.response;

import lombok.Builder;
import mouda.backend.core.domain.please.Please;

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
