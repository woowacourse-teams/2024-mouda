package mouda.backend.moim.presentation.response.chamyo;

import lombok.Builder;
import mouda.backend.moim.domain.Chamyo;

@Builder
public record ChamyoFindAllResponse(
	long darakbangMemberId,
	String nickname,
	String profile,
	String role
) {

	public static ChamyoFindAllResponse toResponse(Chamyo chamyo) {
		return ChamyoFindAllResponse.builder()
			.darakbangMemberId(chamyo.getDarakbangMember().getId())
			.nickname(chamyo.getDarakbangMember().getNickname())
			.profile(chamyo.getDarakbangMember().getProfile())
			.role(chamyo.getMoimRole().name())
			.build();
	}
}
