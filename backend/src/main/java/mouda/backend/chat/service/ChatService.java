package mouda.backend.chat.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.dto.request.ChatCreateRequest;
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
			.orElseThrow();
		Chat chat = chatCreateRequest.toEntity(moim, member);
		chatRepository.save(chat);
	}
}
