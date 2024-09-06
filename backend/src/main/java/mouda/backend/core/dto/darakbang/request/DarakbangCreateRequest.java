package mouda.backend.core.dto.darakbang.request;

import jakarta.validation.constraints.NotNull;
import mouda.backend.core.domain.darakbang.Darakbang;

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
