package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Participant;
import mouda.backend.chat.domain.Target;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Component
@RequiredArgsConstructor
public class MoimChatPreviewManager implements ChatPreviewManager {

	private final ChamyoRepository chamyoRepository;
	private final MoimFinder moimFinder;
	private final ChatRoomFinder chatRoomFinder;

	@Override
	public List<ChatPreview> create(DarakbangMember darakbangMember) {
		List<Moim> myMoims = moimFinder.readAllMyMoims(darakbangMember);

		return myMoims.stream()
			.filter(Moim::isChatOpened)
			.map(this::getChatPreview)
			.sorted()
			.toList();
	}

	private ChatPreview getChatPreview(Moim moim) {
		long targetId = moim.getId();
		ChatRoom chatRoom = chatRoomFinder.readChatRoomByTargetId(targetId, ChatRoomType.MOIM);
		long lastReadChatId = chamyoRepository.findLastReadChatIdByMoimId(targetId);
		List<Participant> participants = chamyoRepository.findAllByMoimId(targetId)
			.stream()
			.map(chamyo -> new Participant(
				moim.getDarakbangId(),
				chamyo.getDarakbangMember().getNickname(),
				chamyo.getDarakbangMember().getProfile(),
				chamyo.getDarakbangMember().getRole().toString()))
			.toList();

		return ChatPreview.builder()
			.chatRoom(chatRoom)
			.target(new Target(moim))
			.lastReadChatId(lastReadChatId)
			.participants(participants)
			.build();
	}
}
