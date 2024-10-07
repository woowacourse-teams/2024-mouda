package mouda.backend.chat.presentation.response;

import lombok.Builder;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimChat;

@Builder
public record ChatPreviewResponse(
	Long targetId,
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
			.targetId(moim.getId())
			.title(moim.getTitle())
			.currentPeople(moimChat.getCurrentPeople())
			.isStarted(moim.isChatOpened())
			.lastContent(moimChat.getLastContent())
			.lastReadChatId(chamyo.getLastReadChatId())
			.build();
	}

	public static ChatPreviewResponse toResponse(BetEntity betEntity, ChatEntity lastChat, int currentPeople,
		long lastReadChatId) {
		return ChatPreviewResponse.builder()
			.targetId(betEntity.getId())
			.title(betEntity.getTitle())
			.currentPeople(currentPeople)
			.lastContent(lastChat.getContent())
			.lastReadChatId(lastReadChatId)
			.build();
	}

	public static ChatPreviewResponse toResponse(Moim moim, int currentPeople, ChatEntity lastChatEntity,
		long lastReadChatId) {
		return ChatPreviewResponse.builder()
			.targetId(moim.getId())
			.title(moim.getTitle())
			.currentPeople(currentPeople)
			.isStarted(moim.isChatOpened())
			.lastContent(lastChatEntity.getContent())
			.lastReadChatId(lastReadChatId)
			.build();
	}

	public static ChatPreviewResponse toResponse(Moim moim, ChatPreview chatPreview) {
		return ChatPreviewResponse.builder()
			.targetId(moim.getId())
			.title(moim.getTitle())
			.isStarted(moim.isChatOpened())
			.currentPeople(chatPreview.getCurrentPeople())
			.lastContent(chatPreview.getLastContent())
			.lastReadChatId(chatPreview.getLastReadChatId())
			.build();
	}
}
