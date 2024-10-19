package mouda.backend.auth.presentation.request;

import jakarta.validation.constraints.NotNull;

public record KakaoLoginRequest(
	@NotNull
	String code
) {
}
