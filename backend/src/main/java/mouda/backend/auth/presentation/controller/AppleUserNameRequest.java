package mouda.backend.auth.presentation.controller;

public record AppleUserNameRequest(
	String firstName,
	String lastName
) {
}
