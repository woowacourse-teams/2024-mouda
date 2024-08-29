package mouda.backend.chamyo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;

@Builder
@Schema(name = "참여자 조회 응답", description = "모임의 참여자를 조회할 때 사용")
public record ChamyoFindAllResponse(
	@Schema(description = "닉네임", example = "닉네임")
	String nickname,
	@Schema(description = "프로필 사진 URL", example = "https://example.com/profile.jpg")
	String profile,
	@Schema(description = "방장 / 참여자 / 미참여자 등 해당 모임에 대한 회원의 상태", example = "MOIMER")
	MoimRole role
) {

	public static ChamyoFindAllResponse toResponse(Chamyo chamyo) {
		return ChamyoFindAllResponse.builder()
			.nickname(chamyo.getDarakbangMember().getNickname())
			.profile("")
			.role(chamyo.getMoimRole())
			.build();
	}
}
