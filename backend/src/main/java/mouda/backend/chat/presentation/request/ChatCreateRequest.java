package mouda.backend.chat.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record ChatCreateRequest(
	@NotBlank
	String content
) {
}
