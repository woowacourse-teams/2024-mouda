package mouda.backend.core.dto.darakbang.response;

import lombok.Builder;
import mouda.backend.core.domain.darakbang.DarakBangMemberRole;

@Builder
public record DarakbangMemberRoleResponse(
	String role
) {
	private static final String EMPTY_ROLE = "OUTSIDER";

	public static DarakbangMemberRoleResponse toResponse(DarakBangMemberRole role) {
		return DarakbangMemberRoleResponse.builder()
			.role(role.name())
			.build();
	}

	public static DarakbangMemberRoleResponse toResponse() {
		return DarakbangMemberRoleResponse.builder()
			.role(EMPTY_ROLE)
			.build();
	}
}
