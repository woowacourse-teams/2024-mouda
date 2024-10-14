package mouda.backend.auth.presentation.request;

public record AppleOauthRequest(
	Long memberId,
	String nonce
) {
}
