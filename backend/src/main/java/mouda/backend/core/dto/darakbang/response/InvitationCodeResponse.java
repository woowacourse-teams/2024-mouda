package mouda.backend.core.dto.darakbang.response;

import lombok.Builder;
import mouda.backend.core.domain.darakbang.Darakbang;

@Builder
public record InvitationCodeResponse(
	String code
) {

	public static InvitationCodeResponse toResponse(Darakbang darakbang) {
		return InvitationCodeResponse.builder()
			.code(darakbang.getCode())
			.build();
	}
}
