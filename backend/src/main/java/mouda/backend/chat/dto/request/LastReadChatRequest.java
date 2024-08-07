package mouda.backend.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record LastReadChatRequest(
	@NotNull
	Long moimId,

	@NotNull
	Long lastReadChatId
) {
}
