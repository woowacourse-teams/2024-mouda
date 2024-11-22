package mouda.backend.auth.presentation.response;

public record LoginResponse(
	String accessToken,
	boolean isConverted
) {
}
