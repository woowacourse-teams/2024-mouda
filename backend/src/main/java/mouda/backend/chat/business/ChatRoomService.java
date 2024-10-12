package mouda.backend.chat.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.ChatRoomDetails;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.implement.ChatPreviewManager;
import mouda.backend.chat.implement.ChatPreviewManagerRegistry;
import mouda.backend.chat.implement.ChatRoomDetailsFinder;
import mouda.backend.chat.implement.ChatRoomWriter;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.chat.presentation.response.ChatRoomDetailsResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.writer.MoimWriter;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomDetailsFinder chatRoomDetailsFinder;
	private final ChatPreviewManagerRegistry chatPreviewManagerRegistry;
	private final ChatRoomWriter chatRoomWriter;
	private final MoimFinder moimFinder;
	private final MoimWriter moimWriter;

	@Transactional(readOnly = true)
	public ChatRoomDetailsResponse findChatRoomDetails(long darakbangId, long chatRoomId, DarakbangMember darakbangMember) {
		ChatRoomDetails chatRoomDetails = chatRoomDetailsFinder.find(darakbangId, chatRoomId, darakbangMember);

		return ChatRoomDetailsResponse.from(chatRoomDetails);
	}

	@Transactional(readOnly = true)
	public ChatPreviewResponses findChatPreview(DarakbangMember darakbangMember, ChatRoomType chatRoomType) {
		ChatPreviewManager manager = chatPreviewManagerRegistry.getManager(chatRoomType);
		List<ChatPreview> chatPreviews = manager.create(darakbangMember);

		return ChatPreviewResponses.toResponse(chatPreviews);
	}

	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		moimWriter.openChatByMoimer(moim, darakbangMember);
		chatRoomWriter.append(moimId, darakbangId, ChatRoomType.MOIM);
	}
}
