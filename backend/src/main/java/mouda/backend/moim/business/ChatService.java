package mouda.backend.moim.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatRooms;
import mouda.backend.moim.domain.ChatWithAuthor;
import mouda.backend.moim.domain.Chats;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimChat;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.ChatFinder;
import mouda.backend.moim.implement.finder.ChatRoomFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.sender.OldChatNotificationSender;
import mouda.backend.moim.implement.validator.ChamyoValidator;
import mouda.backend.moim.implement.writer.ChatWriter;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.moim.presentation.request.chat.OldChatCreateRequest;
import mouda.backend.moim.presentation.request.chat.OldDateTimeConfirmRequest;
import mouda.backend.moim.presentation.request.chat.OldLastReadChatRequest;
import mouda.backend.moim.presentation.request.chat.OldPlaceConfirmRequest;
import mouda.backend.moim.presentation.response.chat.OldChatFindUnloadedResponse;
import mouda.backend.moim.presentation.response.chat.OldChatPreviewResponses;
import mouda.backend.notification.domain.NotificationType;

@Transactional
@Service(value = "oldChatService")
@RequiredArgsConstructor
public class ChatService {

	private final MoimFinder moimFinder;
	private final MoimWriter moimWriter;
	private final ChatFinder chatFinder;
	private final ChatWriter chatWriter;
	private final ChamyoValidator chamyoValidator;
	private final ChamyoFinder chamyoFinder;
	private final ChatRoomFinder chatRoomFinder;
	private final OldChatNotificationSender oldChatNotificationSender;

	public void createChat(long darakbangId, OldChatCreateRequest oldChatCreateRequest, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(oldChatCreateRequest.moimId(), darakbangId);
		chamyoValidator.validateMemberChamyoMoim(moim, darakbangMember);

		Chat chat = oldChatCreateRequest.toEntity(moim, darakbangMember);
		chatWriter.save(chat);

		oldChatNotificationSender.sendChatNotification(moim, darakbangMember, NotificationType.NEW_CHAT);
	}

	@Transactional(readOnly = true)
	public OldChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long moimId, DarakbangMember darakbangMember
	) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		chamyoValidator.validateMemberChamyoMoim(moim, darakbangMember);

		Chats chats = chatFinder.readAllUnloadedChats(moimId, recentChatId);
		List<ChatWithAuthor> chatWithAuthors = chats.getChatsWithAuthor(darakbangMember);

		return OldChatFindUnloadedResponse.toResponse(chatWithAuthors);
	}

	public void confirmPlace(
		long darakbangId, OldPlaceConfirmRequest request, DarakbangMember darakbangMember
	) {
		Moim moim = moimFinder.read(request.moimId(), darakbangId);
		moimWriter.confirmPlace(moim, darakbangMember, request.place());

		Chat chat = request.toEntity(moim, darakbangMember);
		chatWriter.save(chat);

		oldChatNotificationSender.sendChatNotification(moim, darakbangMember, NotificationType.MOIM_PLACE_CONFIRMED);
	}

	public void confirmDateTime(
		long darakbangId, OldDateTimeConfirmRequest request, DarakbangMember darakbangMember
	) {
		Moim moim = moimFinder.read(request.moimId(), darakbangId);
		moimWriter.confirmDateTime(moim, darakbangMember, request.date(), request.time());

		Chat chat = request.toEntity(moim, darakbangMember);
		chatWriter.save(chat);

		oldChatNotificationSender.sendChatNotification(moim, darakbangMember, NotificationType.MOIM_TIME_CONFIRMED);
	}

	public OldChatPreviewResponses findChatPreview(long darakbangId, DarakbangMember darakbangMember) {
		ChatRooms chatRooms = chatRoomFinder.findAllOrderByLastChat(darakbangId, darakbangMember);
		List<MoimChat> moimChats = chatRooms.getMoimChats();

		return OldChatPreviewResponses.toResponse(moimChats);
	}

	public void createLastChat(
		long darakbangId, long moimId, OldLastReadChatRequest oldLastReadChatRequest, DarakbangMember darakbangMember
	) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		Chamyo chamyo = chamyoFinder.read(moim, darakbangMember);

		chamyo.updateLastChat(oldLastReadChatRequest.lastReadChatId());
	}

	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		moimWriter.openChatByMoimer(moim, darakbangMember);
	}
}
