package mouda.backend.chat.presentation.request;

import jakarta.validation.constraints.NotNull;

public record LastReadChatRequest(
	@NotNull
	Long lastReadChatId
) {
}
