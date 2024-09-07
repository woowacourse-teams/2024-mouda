package mouda.backend.darakbangmember.presentation.response;

import java.util.List;

import lombok.Builder;

@Builder
public record DarakbangMemberResponses(
	List<DarakbangMemberResponse> darakbangMemberResponses
) {

	public static DarakbangMemberResponses toResponse(List<DarakbangMemberResponse> darakbangMemberResponse) {
		return DarakbangMemberResponses.builder()
			.darakbangMemberResponses(darakbangMemberResponse)
			.build();
	}
}
