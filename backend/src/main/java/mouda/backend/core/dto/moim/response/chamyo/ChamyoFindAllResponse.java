package mouda.backend.core.dto.moim.response.chamyo;

import lombok.Builder;
import mouda.backend.core.domain.moim.Chamyo;

@Builder
public record ChamyoFindAllResponse(
	String nickname,
	String profile,
	String role
) {

	public static ChamyoFindAllResponse toResponse(Chamyo chamyo) {
		return ChamyoFindAllResponse.builder()
			.nickname(chamyo.getDarakbangMember().getNickname())
			.profile("")
			.role(chamyo.getMoimRole().name())
			.build();
	}
}
