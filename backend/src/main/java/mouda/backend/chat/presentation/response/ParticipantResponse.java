package mouda.backend.chat.presentation.response;

public record ParticipantResponse(
	String nickname,
	String profile,
	String role
) {

	public ParticipantResponse(String nickname, String profile) {
		this(nickname, profile, null);
	}
}
