package mouda.backend.chat.presentation.response;

import lombok.Builder;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.Target;

@Builder
public record ChatPreviewResponse(
	Long targetId,
	String title,
	int currentPeople,
	boolean isStarted,
	String lastContent,
	long lastReadChatId
) {

	public static ChatPreviewResponse toResponse(ChatPreview chatPreview) {
		Target target = chatPreview.getTarget();
		return ChatPreviewResponse.builder()
			.targetId(target.getTargetId())
			.title(target.getTitle())
			.currentPeople(chatPreview.getCurrentPeople())
			.lastContent(chatPreview.getLastContent())
			.lastReadChatId(chatPreview.getLastReadChatId())
			.build();
	}
}
