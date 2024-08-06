package mouda.backend.chat.dto.response;

import lombok.Builder;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chat.domain.Chat;

@Builder
public record ChatPreviewResponse(
	Long moimId,
	String title,
	int currentPeople,
	boolean beforeMoim,
	String lastContent,
	long unreadContentCount
) {

	public static ChatPreviewResponse toResponse(
		Chat chat,
		Chamyo chamyo,
		int currentPeople,
		long unreadContentCount
	) {
		return ChatPreviewResponse.builder()
			.moimId(chamyo.getMoim().getId())
			.title(chamyo.getMoim().getTitle())
			.currentPeople(currentPeople)
			.beforeMoim(chamyo.getMoim().isPastMoim())
			.lastContent(chat.getContent())
			.unreadContentCount(unreadContentCount)
			.build();
	}
}
