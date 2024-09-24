package mouda.backend.darakbangmember.presentation.response;

import java.util.List;

import lombok.Builder;
import mouda.backend.darakbangmember.domain.DarakbangMembers;

@Builder
public record DarakbangMemberResponses(
	List<DarakbangMemberResponse> responses
) {

	public static DarakbangMemberResponses toResponse(DarakbangMembers darakbangMembers) {
		return DarakbangMemberResponses.builder().responses(
			darakbangMembers.getDarakbangMembers()
				.stream()
				.map(DarakbangMemberResponse::toResponse)
				.toList()
		).build();
	}
}
