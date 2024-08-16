package mouda.backend.darakbang.dto.response;

import lombok.Builder;
import mouda.backend.darakbang.domain.Darakbang;

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
