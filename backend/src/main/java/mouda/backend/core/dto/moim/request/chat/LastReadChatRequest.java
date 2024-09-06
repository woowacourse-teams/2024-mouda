package mouda.backend.core.dto.moim.request.chat;

import jakarta.validation.constraints.NotNull;

public record LastReadChatRequest(
	@NotNull
	Long moimId,

	@NotNull
	Long lastReadChatId
) {
}
