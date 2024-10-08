package mouda.backend.member.presentation.response;

public record MemberFindResponse(
	String name,
	String nickname,
	String profile,
	String description
) {
}
