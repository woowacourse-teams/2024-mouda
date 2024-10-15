package mouda.backend.chat.implement;

import mouda.backend.chat.domain.Attributes;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.darakbangmember.domain.DarakbangMember;

public interface AttributeManager {

	boolean support(ChatRoomType chatRoomType);

	Attributes create(ChatRoom chatRoom, DarakbangMember darakbangMember);
}
