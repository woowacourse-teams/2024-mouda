package mouda.backend.auth.presentation.request;

import jakarta.validation.constraints.NotNull;

public record AppleOauthRequest(
	Long memberId,

	@NotNull
	String code
) {
}
