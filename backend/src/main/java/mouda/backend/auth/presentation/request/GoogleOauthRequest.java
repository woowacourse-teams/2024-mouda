package mouda.backend.auth.presentation.request;

import jakarta.validation.constraints.NotNull;

public record GoogleOauthRequest(
	@NotNull
	String idToken
) {
}
