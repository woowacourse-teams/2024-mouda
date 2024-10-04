package mouda.backend.auth.business.result;

public record LoginProcessResult(
	Long memberId,
	String accessToken
) {
}
