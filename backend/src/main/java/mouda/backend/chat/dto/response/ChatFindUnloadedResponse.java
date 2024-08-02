package mouda.backend.chat.dto.response;

import java.util.List;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
}
