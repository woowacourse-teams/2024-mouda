package mouda.backend.chat.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetRole;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.Attributes;
import mouda.backend.chat.domain.BetAttributes;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Loser;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetAttributeManager implements AttributeManager {

	private final BetFinder betFinder;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	@Override
	public boolean support(ChatRoomType chatRoomType) {
		return chatRoomType == ChatRoomType.BET;
	}

	@Override
	public Attributes create(ChatRoom chatRoom, DarakbangMember darakbangMember) {
		Bet bet = betFinder.find(darakbangMember.getDarakbang().getId(), chatRoom.getTargetId());
		boolean isLoser = bet.isLoser(darakbangMember.getId());
		Loser loser = getLoser(bet, darakbangMember.getId());
		return new BetAttributes(bet.getBetDetails().getTitle(), isLoser, bet.getId(), loser);
	}

	private Loser getLoser(Bet bet, long requestDarakbangMemberId) {
		DarakbangMember darakbangMember = betDarakbangMemberRepository.findByBetIdAndDarakbangMemberId(bet.getId(), bet.getLoserId())
			.orElseThrow(IllegalArgumentException::new).getDarakbangMember(); // TODO: 예외 처리 리팩토링
		BetRole betRole = getBetRole(requestDarakbangMemberId, bet.getMoimerId());
		return new Loser(darakbangMember.getNickname(), darakbangMember.getProfile(), betRole.toString());
	}

	private BetRole getBetRole(long requestDarakbangMemberId, long moimerId) {
		return moimerId == requestDarakbangMemberId ? BetRole.MOIMER : BetRole.MOIMEE;
	}
}
