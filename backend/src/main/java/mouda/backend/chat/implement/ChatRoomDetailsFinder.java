package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.Attributes;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomDetails;
import mouda.backend.chat.domain.Participant;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class ChatRoomDetailsFinder {

	private final ChatRoomFinder chatRoomFinder;
	private final AttributeManagerRegistry attributeManagerRegistry;
	private final ParticipantResolverRegistry participantResolverRegistry;

	@Transactional(readOnly = true)
	public ChatRoomDetails find(long darakbangId, long chatRoomId, DarakbangMember darakbangMember) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);
		Attributes attributes = attributeManagerRegistry.getManager(chatRoom.getType()).create(chatRoom, darakbangMember);
		List<Participant> participants = participantResolverRegistry.getResolver(chatRoom.getType()).resolve(chatRoom);

		return new ChatRoomDetails(chatRoomId, chatRoom.getType(), attributes, participants);
	}
}
