package mouda.backend.chamyo.dto.response;

import lombok.Builder;
import mouda.backend.chamyo.domain.Chamyo;

@Builder
public record ChamyoFindAllResponse(
	String nickname,
	String profile,
	String role
) {

	public static ChamyoFindAllResponse toResponse(Chamyo chamyo) {
		return ChamyoFindAllResponse.builder()
			.nickname(chamyo.getMember().getNickname())
			.profile("")
			.role(chamyo.getMoimRole().name())
			.build();
	}
}
