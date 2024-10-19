package mouda.backend.auth.presentation.request;

import jakarta.validation.constraints.NotNull;

public record GoogleLoginRequest(
	@NotNull
	String identityToken
) {
}
