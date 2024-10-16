package mouda.backend.chat.presentation.response;

public record ParticipantResponse(
	String nickname,
	String profile,
	String role
) {
}
