package mouda.backend.chat.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomDetails;
import mouda.backend.chat.implement.ChatRoomDetailsFinder;
import mouda.backend.chat.presentation.response.ChatRoomDetailsResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomDetailsFinder chatRoomDetailsFinder;

	@Transactional(readOnly = true)
	public ChatRoomDetailsResponse findChatRoomDetails(long darakbangId, long chatRoomId, DarakbangMember darakbangMember) {
		ChatRoomDetails chatRoomDetails = chatRoomDetailsFinder.find(darakbangId, chatRoomId, darakbangMember);

		return ChatRoomDetailsResponse.from(chatRoomDetails);
	}
}
