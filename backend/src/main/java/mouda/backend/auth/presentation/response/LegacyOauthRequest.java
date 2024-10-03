package mouda.backend.auth.presentation.response;

import jakarta.validation.constraints.NotNull;

public record LegacyOauthRequest(
	Long memberId,
	
	@NotNull
	String code
) {
}
