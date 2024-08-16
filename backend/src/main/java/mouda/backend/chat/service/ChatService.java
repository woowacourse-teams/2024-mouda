package mouda.backend.chat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.DateTimeConfirmRequest;
import mouda.backend.chat.dto.request.LastReadChatRequest;
import mouda.backend.chat.dto.request.PlaceConfirmRequest;
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

@Transactional
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;

	public void createChat(ChatCreateRequest chatCreateRequest, Member member) {
		Moim moim = findMoimByMoimId(chatCreateRequest.moimId());
		findChamyoByMoimIdAndMemberId(chatCreateRequest.moimId(), member.getId());

		Chat chat = chatCreateRequest.toEntity(moim, member);
		chatRepository.save(chat);
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(long recentChatId, long moimId, Member member) {
		findMoimByMoimId(moimId);
		findChamyoByMoimIdAndMemberId(moimId, member.getId());
		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}

		List<ChatFindDetailResponse> chats = chatRepository.findAllUnloadedChats(moimId, recentChatId).stream()
			.map(chat -> ChatFindDetailResponse.toResponse(chat, chat.isMyMessage(member.getId())))
			.toList();

		return new ChatFindUnloadedResponse(chats);
	}

	public void confirmPlace(PlaceConfirmRequest placeConfirmRequest, Member member) {
		Moim moim = findMoimByMoimId(placeConfirmRequest.moimId());
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(placeConfirmRequest.moimId(), member.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_PLACE);
		}

		Chat chat = placeConfirmRequest.toEntity(moim, member);
		moim.confirmPlace(placeConfirmRequest.place());
		chatRepository.save(chat);
	}

	public void confirmDateTime(DateTimeConfirmRequest dateTimeConfirmRequest, Member member) {
		Moim moim = findMoimByMoimId(dateTimeConfirmRequest.moimId());
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(dateTimeConfirmRequest.moimId(), member.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_DATETIME);
		}

		Chat chat = dateTimeConfirmRequest.toEntity(moim, member);
		moim.confirmDateTime(dateTimeConfirmRequest.date(), dateTimeConfirmRequest.time());
		chatRepository.save(chat);
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
		String lastContent = lastChat.map(Chat::getContent).orElse("");

		return ChatPreviewResponse.toResponse(chamyo, currentPeople, lastContent);
	}

	public void createLastChat(LastReadChatRequest lastReadChatRequest, Member member) {
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(lastReadChatRequest.moimId(), member.getId());

		chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
	}

	public void openChatRoom(Long moimId, Member member) {
		Moim moim = findMoimByMoimId(moimId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, member.getId());
		if (chamyo.getMoimRole() == MoimRole.MOIMER) {
			moim.openChat();
		}
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NO_PERMISSION_OPEN_CHAT);
		}
	}

	private Moim findMoimByMoimId(long moimId) {
		return moimRepository.findById(moimId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND));
	}

	private Chamyo findChamyoByMoimIdAndMemberId(long moimId, long memberId) {
		return chamyoRepository.findByMoimIdAndMemberId(moimId, memberId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_FIND));
	}
}