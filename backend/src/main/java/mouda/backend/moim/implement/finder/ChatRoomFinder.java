package mouda.backend.moim.implement.finder;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatRoom;
import mouda.backend.moim.domain.ChatRooms;

@Component
@RequiredArgsConstructor
public class ChatRoomFinder {

	private final ChamyoFinder chamyoFinder;
	private final ChatFinder chatFinder;
	private final MoimFinder moimFinder;

	public ChatRooms findAllOrderByLastChat(long darakbangId, DarakbangMember darakbangMember) {
		List<ChatRoom> chatRooms = chamyoFinder.readAllChatOpened(darakbangId, darakbangMember).stream()
			.map(this::createChatRoom)
			.sorted()
			.toList();
		return new ChatRooms(chatRooms);
	}

	private ChatRoom createChatRoom(Chamyo chamyo) {
		Chat lastChat = chatFinder.readLastChat(chamyo.getMoim().getId());
		int currentPeople = moimFinder.countCurrentPeople(chamyo.getMoim());
		return new ChatRoom(chamyo, lastChat, currentPeople);
	}
}
