package mouda.backend.moim.presentation.request.chat;

import jakarta.validation.constraints.NotNull;

public record LastReadChatRequest(
	@NotNull
	Long moimId,

	@NotNull
	Long lastReadChatId
) {
}
