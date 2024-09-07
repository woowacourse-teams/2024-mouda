package mouda.backend.darakbang.presentation.request;

import jakarta.validation.constraints.NotNull;
import mouda.backend.darakbang.domain.Darakbang;

public record DarakbangCreateRequest(
	@NotNull
	String name,

	@NotNull
	String nickname
) {
	public Darakbang toEntity(String code) {
		return Darakbang.builder()
			.name(name)
			.code(code)
			.build();
	}
}
