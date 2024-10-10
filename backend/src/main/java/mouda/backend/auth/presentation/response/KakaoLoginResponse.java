package mouda.backend.auth.presentation.response;

public record KakaoLoginResponse(
	Long memberId,
	String accessToken
) {
}
