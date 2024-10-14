package mouda.backend.chat.presentation.response;

public record ChatRoomParticipantResponse(
	String nickname,
	String profile,
	String role
) {
}
