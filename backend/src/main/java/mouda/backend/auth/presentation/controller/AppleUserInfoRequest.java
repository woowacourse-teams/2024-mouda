package mouda.backend.auth.presentation.controller;

public record AppleUserInfoRequest(
	AppleUserNameRequest name,
	String email
) {
}
