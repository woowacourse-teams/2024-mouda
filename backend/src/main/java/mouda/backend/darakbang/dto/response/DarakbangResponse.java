package mouda.backend.darakbang.dto.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Builder
public record DarakbangResponse(
	long darakbangId,
	String name
) {

	public static DarakbangResponse toResponse(DarakbangMember darakbangMember) {
		return DarakbangResponse.builder()
			.darakbangId(darakbangMember.getId())
			.name(darakbangMember.getDarakbangName())
			.build();
	}
}
