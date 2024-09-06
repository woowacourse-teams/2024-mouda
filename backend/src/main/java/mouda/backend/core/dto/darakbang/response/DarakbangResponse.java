package mouda.backend.core.dto.darakbang.response;

import lombok.Builder;
import mouda.backend.core.domain.darakbang.DarakbangMember;

@Builder
public record DarakbangResponse(
	long darakbangId,
	String name
) {

	public static DarakbangResponse toResponse(DarakbangMember darakbangMember) {
		return DarakbangResponse.builder()
			.darakbangId(darakbangMember.getDarakbang().getId())
			.name(darakbangMember.getDarakbangName())
			.build();
	}
}
