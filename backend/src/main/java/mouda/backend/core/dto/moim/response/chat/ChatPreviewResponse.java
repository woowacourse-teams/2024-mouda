package mouda.backend.core.dto.moim.response.chat;

import lombok.Builder;
import mouda.backend.core.domain.moim.Chamyo;

@Builder
public record ChatPreviewResponse(
	Long moimId,
	String title,
	int currentPeople,
	boolean isStarted,
	String lastContent,
	long lastReadChatId
) {

	public static ChatPreviewResponse toResponse(
		Chamyo chamyo,
		int currentPeople,
		String lastContent
	) {
		return ChatPreviewResponse.builder()
			.moimId(chamyo.getMoim().getId())
			.title(chamyo.getMoim().getTitle())
			.currentPeople(currentPeople)
			.isStarted(chamyo.getMoim().isPastMoim())
			.lastContent(lastContent)
			.lastReadChatId(chamyo.getLastReadChatId())
			.build();
	}
}
