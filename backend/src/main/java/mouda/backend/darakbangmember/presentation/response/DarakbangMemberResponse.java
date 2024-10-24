package mouda.backend.darakbangmember.presentation.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Builder
public record DarakbangMemberResponse(
	long darakbangMemberId,
	String nickname,
	String profile
) {

	public static DarakbangMemberResponse toResponse(DarakbangMember darakbangMember) {
		return DarakbangMemberResponse.builder()
			.darakbangMemberId(darakbangMember.getId())
			.nickname(darakbangMember.getNickname())
			.profile(darakbangMember.getProfile())
			.build();
	}
}
