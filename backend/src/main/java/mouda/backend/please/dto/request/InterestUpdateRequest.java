package mouda.backend.please.dto.request;

public record InterestUpdateRequest(
	long pleaseId,
	boolean isInterested
) {
}
