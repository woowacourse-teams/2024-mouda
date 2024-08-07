package mouda.backend.chat.dto.response;

import lombok.Builder;
import mouda.backend.chamyo.domain.Chamyo;

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
		Chamyo chamyo,
		int currentPeople,
		String lastContent,
		long unreadContentCount
	) {
		return ChatPreviewResponse.builder()
			.moimId(chamyo.getMoim().getId())
			.title(chamyo.getMoim().getTitle())
			.currentPeople(currentPeople)
			.beforeMoim(chamyo.getMoim().isPastMoim())
			.lastContent(lastContent)
			.unreadContentCount(unreadContentCount)
			.build();
	}

	public static ChatPreviewResponse toResponse(
		Chamyo chamyo,
		int currentPeople
	) {
		return ChatPreviewResponse.builder()
			.moimId(chamyo.getMoim().getId())
			.title(chamyo.getMoim().getTitle())
			.currentPeople(currentPeople)
			.beforeMoim(chamyo.getMoim().isPastMoim())
			.lastContent("")
			.unreadContentCount(0)
			.build();
	}
}
