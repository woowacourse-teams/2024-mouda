package mouda.backend.bet.presentation.response;

import mouda.backend.bet.domain.Participant;

public record ParticipantResponse(
	String nickname,
	long id,
	String profileUrl
) {
	public static ParticipantResponse from(Participant participant) {
		return new ParticipantResponse(
			participant.getName(),
			participant.getId(),
			"" // TODO : profileUrl 추가
		);
	}
}
