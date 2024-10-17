package mouda.backend.chat.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatOwnership;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.Chats;
import mouda.backend.chat.implement.ChatRoomFinder;
import mouda.backend.chat.implement.ChatWriter;
import mouda.backend.chat.implement.sender.ChatNotificationSender;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.chat.util.DateTimeFormatter;
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
	private final ChatNotificationSender chatNotificationSender;

	public void createChat(
		long darakbangId,
		long chatRoomId,
		ChatCreateRequest request,
		DarakbangMember darakbangMember
	) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);

		String content = request.content();
		chatWriter.append(chatRoom.getId(), content, darakbangMember);

		chatNotificationSender.sendChatNotification(darakbangId, chatRoom, content, darakbangMember,
			NotificationType.NEW_CHAT);
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long chatRoomId, DarakbangMember darakbangMember
	) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);

		Chats chats = chatRoomFinder.findAllUnloadedChats(chatRoom.getId(), recentChatId);
		List<ChatOwnership> chatsWithAuthor = chats.getChatsWithAuthor(darakbangMember);

		return ChatFindUnloadedResponse.toResponse(chatsWithAuthor);
	}

	public void confirmPlace(long darakbangId, long chatRoomId, PlaceConfirmRequest request,
		DarakbangMember darakbangMember) {
		ChatRoom chatRoom = chatRoomFinder.readMoimChatRoom(darakbangId, chatRoomId);
		String place = request.place();

		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		moimWriter.confirmPlace(moim, darakbangMember, place);

		chatWriter.appendPlaceTypeChat(chatRoom.getId(), place, darakbangMember);

		chatNotificationSender.sendChatNotification(darakbangId, chatRoom, place, darakbangMember,
			NotificationType.MOIM_PLACE_CONFIRMED);
	}

	public void confirmDateTime(long darakbangId, long chatRoomId, DateTimeConfirmRequest request,
		DarakbangMember darakbangMember) {
		ChatRoom chatRoom = chatRoomFinder.readMoimChatRoom(darakbangId, chatRoomId);

		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		LocalDate date = request.date();
		LocalTime time = request.time();
		moimWriter.confirmDateTime(moim, darakbangMember, date, time);

		chatWriter.appendDateTimeTypeChat(chatRoom.getId(), date, time, darakbangMember);

		chatNotificationSender.sendChatNotification(darakbangId, chatRoom, DateTimeFormatter.formatDateTime(date, time),
			darakbangMember, NotificationType.MOIM_TIME_CONFIRMED);
	}

	public void updateLastReadChat(
		long darakbangId, long chatRoomId, LastReadChatRequest request, DarakbangMember darakbangMember
	) {
		ChatRoom chatRoom = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);

		chatWriter.updateLastReadChat(chatRoom, darakbangMember, request.lastReadChatId());
	}
}
