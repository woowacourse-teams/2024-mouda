package mouda.backend.core.dto.darakbang.response;

import lombok.Builder;
import mouda.backend.core.domain.darakbang.DarakbangMember;

@Builder
public record DarakbangMemberResponse(
	long memberId,
	String nickname,
	String profile
) {

	public static DarakbangMemberResponse toResponse(DarakbangMember darakbangMember) {
		return DarakbangMemberResponse.builder()
			.memberId(darakbangMember.getMemberId())
			.nickname(darakbangMember.getNickname())
			.profile("")
			.build();
	}
}
