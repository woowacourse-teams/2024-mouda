package mouda.backend.chat.implement;

import java.util.List;

import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Participant;

public interface ParticipantsResolver {

	boolean support(ChatRoomType chatRoomType);

	List<Participant> resolve(ChatRoom chatRoom);
}
