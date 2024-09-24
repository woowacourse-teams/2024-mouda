package mouda.backend.darakbang.presentation.request;

import jakarta.validation.constraints.NotNull;

public record DarakbangCreateRequest(
	@NotNull
	String name,

	@NotNull
	String nickname
) {
}
