package mouda.backend.moim.presentation.response.comment;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import mouda.backend.moim.domain.Comment;

@Builder
public record CommentResponse(
	Long commentId,

	String nickname,

	String content,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime dateTime,

	List<ChildCommentResponse> children
) {
	public static CommentResponse toResponse(Comment parentComment, List<Comment> childComments) {
		List<ChildCommentResponse> children = childComments.stream()
			.map(ChildCommentResponse::toResponse)
			.toList();

		return CommentResponse.builder()
			.commentId(parentComment.getId())
			.nickname(parentComment.getAuthorNickname())
			.content(parentComment.getContent())
			.dateTime(parentComment.getCreatedAt())
			.children(children)
			.build();
	}
}
