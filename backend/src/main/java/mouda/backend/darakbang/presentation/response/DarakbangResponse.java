package mouda.backend.darakbang.presentation.response;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakbangMember;

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
