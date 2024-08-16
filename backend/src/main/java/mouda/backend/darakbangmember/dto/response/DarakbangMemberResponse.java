package mouda.backend.darakbangmember.dto.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Builder
public record DarakbangMemberResponse(
	long memberId,
	String nickname,
	String profile
) {

	public static DarakbangMemberResponse toResponse(DarakbangMember darakbangMember) {
		return DarakbangMemberResponse.builder()
			.memberId(darakbangMember.getMemberId())
			.nickname(darakbangMember.getMemberNickname())
			.profile("")
			.build();
	}
}
