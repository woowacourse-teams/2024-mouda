package mouda.backend.chat.presentation.response;

public record ParticipantResponse(
	long darakbangMemberId,
	String nickname,
	String profile,
	String role
) {

	public ParticipantResponse(long darakbangMemberId, String nickname, String profile) {
		this(darakbangMemberId, nickname, profile, null);
	}
}
