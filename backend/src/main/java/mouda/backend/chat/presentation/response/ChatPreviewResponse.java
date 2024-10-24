package mouda.backend.chat.presentation.response;

import java.util.List;

import lombok.Builder;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.Target;

@Builder
public record ChatPreviewResponse(
	Long chatRoomId,
	String title,
	List<ParticipantResponse> participations,
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
			.participations(getParticipants(chatPreview))
			.lastContent(chatPreview.getLastContent())
			.lastReadChatId(chatPreview.getLastReadChatId())
			.build();
	}

	private static List<ParticipantResponse> getParticipants(ChatPreview chatPreview) {
		return chatPreview.getParticipants().stream()
			.map(participant -> new ParticipantResponse(
				participant.getDarakbangMemberId(),
				participant.getNickname(),
				participant.getProfile(),
				participant.getRole()))
			.toList();
	}
}
