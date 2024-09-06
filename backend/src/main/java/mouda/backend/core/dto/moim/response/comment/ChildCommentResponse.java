package mouda.backend.core.dto.moim.response.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import mouda.backend.core.domain.moim.Comment;

@Builder
public record ChildCommentResponse(
	Long commentId,

	String nickname,

	String content,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime dateTime
) {
	public static ChildCommentResponse toResponse(Comment comment) {
		return ChildCommentResponse.builder()
			.commentId(comment.getId())
			.nickname(comment.getAuthorNickname())
			.content(comment.getContent())
			.dateTime(comment.getCreatedAt())
			.build();
	}
}
