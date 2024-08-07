package mouda.backend.chat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.LastReadChatRequest;
import mouda.backend.chat.dto.response.ChatFindDetailResponse;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.repository.ChatRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;

	public void createChat(ChatCreateRequest chatCreateRequest, Member member) {
		Moim moim = moimRepository.findById(chatCreateRequest.moimId())
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND));
		chamyoRepository.findByMoimIdAndMemberId(chatCreateRequest.moimId(), member.getId())
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_SEND));

		Chat chat = chatCreateRequest.toEntity(moim, member);
		chatRepository.save(chat);
	}

	public ChatFindUnloadedResponse findUnloadedChats(long recentChatId, long moimId, Member member) {
		moimRepository.findById(moimId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND));
		chamyoRepository.findByMoimIdAndMemberId(moimId, member.getId())
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_FIND));

		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}

		List<ChatFindDetailResponse> chats = chatRepository.findAllUnloadedChats(moimId, recentChatId).stream()
			.map(chat -> ChatFindDetailResponse.toResponse(chat, chat.isMyMessage(member.getId())))
			.toList();

		return new ChatFindUnloadedResponse(chats);
	}

	public ChatPreviewResponses findChatPreview(Member member) {
		List<ChatPreviewResponse> chatPreviews = chamyoRepository.findAllByMemberId(member.getId()).stream()
			.filter(chamyo -> chamyo.getMoim().isChatOpened())
			.map(this::getChatPreviewResponse)
			.toList();

		return new ChatPreviewResponses(chatPreviews);
	}

	private ChatPreviewResponse getChatPreviewResponse(Chamyo chamyo) {
		Optional<Chat> lastChat = chatRepository.findFirstByMoimIdOrderByIdDesc(
			chamyo.getMoim().getId());

		int currentPeople = chamyoRepository.countByMoim(chamyo.getMoim());
		if (lastChat.isPresent()) {
			String lastContent = lastChat.get().getContent();
			long unreadContentCount = lastChat.get().getId() - chamyo.getLastReadChatId();
			return ChatPreviewResponse.toResponse(chamyo, currentPeople, lastContent, unreadContentCount);
		}

		return ChatPreviewResponse.toResponse(chamyo, currentPeople);
	}

	public void createLastChat(LastReadChatRequest lastReadChatRequest, Member member) {
		Chamyo chamyo = chamyoRepository.findByMoimIdAndMemberId(lastReadChatRequest.moimId(), member.getId())
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_FIND));

		chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
	}
}
