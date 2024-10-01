package mouda.backend.bet.presentation.response;

import java.time.LocalDateTime;
import java.util.List;

import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;

public record BetFindResponse(
	String title,
	int currentParticipants,
	LocalDateTime deadline,
	boolean isAnnounced,
	List<ParticipantResponse> participants,
	BetRole myRole,
	Long chatroomId
) {
	public static BetFindResponse toResponse(Bet bet, DarakbangMember darakbangMember) {
		List<ParticipantResponse> participants = bet.getParticipants().stream()
			.map(ParticipantResponse::from)
			.toList();

		return new BetFindResponse(
			bet.getBetDetails().getTitle(),
			bet.getParticipants().size(),
			bet.getBetDetails().getBettingTime(),
			bet.hasLoser(),
			participants,
			bet.getMyRole(darakbangMember.getId()),
			null
		);
	}
}
