package mouda.backend.moim.presentation.response.chat;

import lombok.Builder;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimChat;

@Builder
public record ChatPreviewResponse(
	Long moimId,
	String title,
	int currentPeople,
	boolean isStarted,
	String lastContent,
	long lastReadChatId
) {

	public static ChatPreviewResponse toResponse(MoimChat moimChat) {
		Chamyo chamyo = moimChat.getChamyo();
		Moim moim = chamyo.getMoim();

		return ChatPreviewResponse.builder()
			.moimId(moim.getId())
			.title(moim.getTitle())
			.currentPeople(moimChat.getCurrentPeople())
			.isStarted(moim.isChatOpened())
			.lastContent(moimChat.getLastContent())
			.lastReadChatId(chamyo.getLastReadChatId())
			.build();
	}
}
