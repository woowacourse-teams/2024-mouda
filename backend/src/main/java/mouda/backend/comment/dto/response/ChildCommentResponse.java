package mouda.backend.comment.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import mouda.backend.comment.domain.Comment;

@Builder
public record ChildCommentResponse(
	Long commentId,
	String nickname,
	String content,
	LocalDateTime dateTime
) {
	public static ChildCommentResponse toResponse(Comment comment) {
		return ChildCommentResponse.builder()
			.commentId(comment.getId())
			.nickname(comment.getMember().getNickname())
			.content(comment.getContent())
			.dateTime(comment.getCreatedAt())
			.build();
	}
}
