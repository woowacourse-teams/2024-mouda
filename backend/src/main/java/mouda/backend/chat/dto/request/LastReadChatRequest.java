package mouda.backend.chat.dto.request;

public record LastReadChatRequest(
	Long moimId,
	Long lastReadChatId
) {
}
