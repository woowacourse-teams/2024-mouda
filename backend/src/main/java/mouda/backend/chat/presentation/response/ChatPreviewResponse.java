package mouda.backend.chat.presentation.response;

import lombok.Builder;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.Target;

@Builder
public record ChatPreviewResponse(
	Long chatRoomId,
	String title,
	int currentPeople,
	boolean isStarted,
	String lastContent,
	long lastReadChatId
) {

	public static ChatPreviewResponse toResponse(ChatPreview chatPreview) {
		Target target = chatPreview.getTarget();
		return ChatPreviewResponse.builder()
			.chatRoomId(chatPreview.getChatRoom().getId())
			.title(target.getTitle())
			.isStarted(target.isStarted())
			.currentPeople(chatPreview.getCurrentPeople())
			.lastContent(chatPreview.getLastContent())
			.lastReadChatId(chatPreview.getLastReadChatId())
			.build();
	}
}
