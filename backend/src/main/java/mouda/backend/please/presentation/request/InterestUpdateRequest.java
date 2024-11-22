package mouda.backend.please.presentation.request;

public record InterestUpdateRequest(
	long pleaseId,
	boolean isInterested
) {
}
