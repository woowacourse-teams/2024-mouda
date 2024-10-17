package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Participant;
import mouda.backend.chat.domain.Target;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetChatPreviewManager implements ChatPreviewManager {

	private final BetDarakbangMemberRepository betDarakbangMemberRepository;
	private final BetFinder betFinder;
	private final ChatRoomFinder chatRoomFinder;

	@Override
	@Transactional(readOnly = true)
	public List<ChatPreview> create(DarakbangMember darakbangMember) {
		List<BetDetails> myBets = betFinder.readAllMyBets(darakbangMember);

		return myBets.stream()
			.filter(BetDetails::hasLoser)
			.map(this::getChatPreview)
			.sorted()
			.toList();
	}

	private ChatPreview getChatPreview(BetDetails bet) {
		long targetId = bet.getId();
		ChatRoom chatRoom = chatRoomFinder.readChatRoomByTargetId(bet.getId(), ChatRoomType.BET);
		long lastReadChatId = betDarakbangMemberRepository.findLastReadChatIdByBetId(targetId);
		List<Participant> participants = betDarakbangMemberRepository.findAllByBetId(targetId).stream()
			.map(betDarakbangMember -> new Participant(betDarakbangMember.getDarakbangMember().getNickname(),
				betDarakbangMember.getDarakbangMember().getProfile(),
				betDarakbangMember.getRole(bet.getMoimerId())))
			.toList();

		return ChatPreview.builder()
			.chatRoom(chatRoom)
			.target(new Target(bet))
			.lastReadChatId(lastReadChatId)
			.participants(participants)
			.build();
	}
}
