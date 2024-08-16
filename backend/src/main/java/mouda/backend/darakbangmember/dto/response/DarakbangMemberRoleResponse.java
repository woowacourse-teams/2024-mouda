package mouda.backend.darakbangmember.dto.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;

@Builder
public record DarakbangMemberRoleResponse(
	DarakBangMemberRole role
) {

	public static DarakbangMemberRoleResponse toResponse(DarakBangMemberRole darakBangMemberRole) {
		return DarakbangMemberRoleResponse.builder()
			.role(darakBangMemberRole)
			.build();
	}
}
