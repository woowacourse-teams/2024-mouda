package mouda.backend.darakbangmember.dto.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Builder
public record DarakbangMemberRoleResponse(
	DarakBangMemberRole role
) {

	public static DarakbangMemberRoleResponse toResponse(DarakbangMember darakbangMember) {
		return DarakbangMemberRoleResponse.builder()
			.role(darakbangMember.getRole())
			.build();
	}
}
