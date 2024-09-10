package mouda.backend.moim.presentation.response.comment;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.ParentComment;

@Builder
public record CommentResponse(
	Long commentId,

	String nickname,

	String content,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime dateTime,

	List<ChildCommentResponse> children
) {

	public static CommentResponse fromParentComment(ParentComment parentComment) {
		List<ChildCommentResponse> children = parentComment.getChildren().stream()
			.map(ChildCommentResponse::toResponse)
			.toList();

		Comment comment = parentComment.getComment();

		return CommentResponse.builder()
			.commentId(comment.getId())
			.nickname(comment.getAuthorNickname())
			.content(comment.getContent())
			.dateTime(comment.getCreatedAt())
			.children(children)
			.build();
	}
}
