package mouda.backend.chat.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomDetails;
import mouda.backend.chat.implement.ChatRoomDetailsFinder;
import mouda.backend.chat.presentation.response.ChatRoomDetailsResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomDetailsFinder chatRoomDetailsFinder;

	public ChatRoomDetailsResponse findChatRoomDetails(long chatRoomId, DarakbangMember darakbangMember) {
		ChatRoomDetails chatRoomDetails = chatRoomDetailsFinder.find(chatRoomId, darakbangMember);

		return ChatRoomDetailsResponse.from(chatRoomDetails);
	}
}
