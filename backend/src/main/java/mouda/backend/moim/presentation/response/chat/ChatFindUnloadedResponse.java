package mouda.backend.moim.presentation.response.chat;

import java.util.List;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
}
