package mouda.backend.auth.presentation.request;

public record AppleUserInfoRequest(
	AppleUserNameRequest name,
	String email
) {
}
