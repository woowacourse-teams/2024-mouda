package mouda.backend.chat.dto.response;

import java.util.List;

public record ChatFindUnloadedResponse(
	long loginMemberId,
	List<ChatFindDetailResponse> chats
) {
}
