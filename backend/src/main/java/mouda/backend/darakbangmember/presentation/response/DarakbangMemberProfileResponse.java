package mouda.backend.darakbangmember.presentation.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Builder
public record DarakbangMemberProfileResponse(
	String name,
	String nickname,
	String profile,
	String description
) {

	public static DarakbangMemberProfileResponse toResponse(DarakbangMember darakbangMember, String name) {
		return DarakbangMemberProfileResponse.builder()
			.name(name)
			.nickname(darakbangMember.getNickname())
			.profile(darakbangMember.getProfile())
			.description(darakbangMember.getDescription())
			.build();
	}
}
