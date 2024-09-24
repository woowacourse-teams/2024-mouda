package mouda.backend.moim.presentation.response.chamyo;

import lombok.Builder;
import mouda.backend.moim.domain.MoimRole;

@Builder
public record MoimRoleFindResponse(
	String role
) {

	public static MoimRoleFindResponse toResponse(MoimRole moimRole) {
		return MoimRoleFindResponse.builder()
			.role(moimRole.name())
			.build();
	}
}
