package mouda.backend.moim.implement.finder;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatRoom;
import mouda.backend.moim.domain.ChatRooms;

@Component
@RequiredArgsConstructor
public class ChatRoomFinder {

	private final ChamyoFinder chamyoFinder;
	private final ChatFinder chatFinder;
	private final MoimFinder moimFinder;

	public ChatRooms findAll(long darakbangId, DarakbangMember darakbangMember) {
		List<ChatRoom> chatRooms = chamyoFinder.readAllOrderByLastChat(darakbangId, darakbangMember).stream()
			.map(chamyo -> {
				Chat lastChat = chatFinder.readLastChat(chamyo.getMoim().getId());
				int currentPeople = moimFinder.countCurrentPeople(chamyo.getMoim());
				return new ChatRoom(chamyo, lastChat, currentPeople);
			})
			.toList();
		return new ChatRooms(chatRooms);
	}

}
