package mouda.backend.darakbang.presentation.request;

import jakarta.validation.constraints.NotNull;

public record DarakbangEnterRequest(
	@NotNull
	String nickname
) {
}
