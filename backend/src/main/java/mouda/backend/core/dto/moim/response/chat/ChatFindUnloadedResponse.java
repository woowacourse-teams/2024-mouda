package mouda.backend.core.dto.moim.response.chat;

import java.util.List;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
}
