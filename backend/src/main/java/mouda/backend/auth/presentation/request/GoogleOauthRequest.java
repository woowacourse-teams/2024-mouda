package mouda.backend.auth.presentation.request;

import jakarta.validation.constraints.NotNull;

public record GoogleOauthReqeust(
	Long memberId,

	@NotNull
	String idToken
) {
}
