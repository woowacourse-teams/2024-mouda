package mouda.backend.chat.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.response.ChatFindDetailResponse;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
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

	public void createChat(ChatCreateRequest chatCreateRequest, Member member) {
		Moim moim = moimRepository.findById(chatCreateRequest.moimId())
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND));
		Chat chat = chatCreateRequest.toEntity(moim, member);
		chatRepository.save(chat);
	}

	public ChatFindUnloadedResponse findUnloadedChats(long recentChatId, long moimId, Member member) {
		List<ChatFindDetailResponse> chats = chatRepository.findAllUnloadedChats(moimId, recentChatId).stream()
			.map(chat -> ChatFindDetailResponse.toResponse(chat, chat.isMyMessage(member.getId())))
			.toList();

		return new ChatFindUnloadedResponse(chats);
	}
}
