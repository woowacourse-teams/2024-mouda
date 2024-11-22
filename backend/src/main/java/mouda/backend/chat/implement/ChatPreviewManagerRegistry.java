package mouda.backend.chat.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;

@Component
@RequiredArgsConstructor
public class ChatPreviewManagerRegistry {

	private final MoimChatPreviewManager moimChatPreviewManager;
	private final BetChatPreviewManager betChatPreviewManager;

	public ChatPreviewManager getManager(ChatRoomType type) {
		if (type == ChatRoomType.MOIM) {
			return moimChatPreviewManager;
		}
		return betChatPreviewManager;
	}
}
