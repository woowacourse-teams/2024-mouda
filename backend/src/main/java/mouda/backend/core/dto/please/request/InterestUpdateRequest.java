package mouda.backend.core.dto.please.request;

public record InterestUpdateRequest(
	long pleaseId,
	boolean isInterested
) {
}
