package mouda.backend.chat.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatWithAuthor;
import mouda.backend.chat.domain.Chats;
import mouda.backend.chat.implement.ChatRoomFinder;
import mouda.backend.chat.implement.ChatWriter;
import mouda.backend.chat.implement.sender.ChatNotificationSender;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.notification.domain.NotificationType;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomFinder chatRoomFinder;
	private final ChatWriter chatWriter;
	private final MoimWriter moimWriter;
	private final MoimFinder moimFinder;
	private final ChatRoomWriter chatRoomWriter;
	private final ChatNotificationSender chatNotificationSender;

	public void createChat(
		long darakbangId,
		long chatRoomId,
		ChatCreateRequest request,
		DarakbangMember darakbangMember
	) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);

		chatWriter.append(chatRoom.getId(), request.content(), darakbangMember);
		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);

		chatNotificationSender.sendChatNotification(moim, darakbangMember, NotificationType.NEW_CHAT, chatRoomId);
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long chatRoomId, DarakbangMember darakbangMember
	) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);

		Chats chats = chatRoomFinder.findAllUnloadedChats(chatRoom.getId(), recentChatId);
		List<ChatWithAuthor> chatsWithAuthor = chats.getChatsWithAuthor(darakbangMember);

		return ChatFindUnloadedResponse.toResponse(chatsWithAuthor);
	}

	public void confirmPlace(long darakbangId, long chatRoomId, PlaceConfirmRequest request,
		DarakbangMember darakbangMember) {
		ChatRoom chatRoom = chatRoomFinder.readMoimChatRoom(darakbangId, chatRoomId);

		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		moimWriter.confirmPlace(moim, darakbangMember, request.place());

		chatWriter.appendPlaceTypeChat(chatRoom.getId(), request.place(), darakbangMember);

		chatNotificationSender.sendChatNotification(moim, darakbangMember, NotificationType.MOIM_PLACE_CONFIRMED, chatRoomId);
	}

	public void confirmDateTime(long darakbangId, long chatRoomId, DateTimeConfirmRequest request,
		DarakbangMember darakbangMember) {
		ChatRoom chatRoom = chatRoomFinder.readMoimChatRoom(darakbangId, chatRoomId);

		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		moimWriter.confirmDateTime(moim, darakbangMember, request.date(), request.time());

		chatWriter.appendDateTimeTypeChat(chatRoom.getId(), request.date(), request.time(), darakbangMember);

		chatNotificationSender.sendChatNotification(moim, darakbangMember, NotificationType.MOIM_TIME_CONFIRMED, chatRoomId);
	}

	public void updateLastReadChat(
		long darakbangId, long chatRoomId, LastReadChatRequest request, DarakbangMember darakbangMember
	) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);

		chatWriter.updateLastReadChat(chatRoom, darakbangMember, request.lastReadChatId());
	}
}
