package mouda.backend.darakbangmember.presentation.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;

@Builder
public record DarakbangMemberRoleResponse(
	String role
) {

	public static DarakbangMemberRoleResponse toResponse(DarakBangMemberRole role) {
		return DarakbangMemberRoleResponse.builder()
			.role(role.name())
			.build();
	}
}
