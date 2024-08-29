package mouda.backend.comment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.comment.domain.Comment;

@Builder
public record CommentResponse(
	@Schema(description = "생성된 댓글 ID", example = "1")
	Long commentId,

	@Schema(description = "댓글 작성자 닉네임", example = "테니")
	String nickname,

	@Schema(description = "댓글 내용", example = "댓글 내용")
	String content,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Schema(description = "댓글 작성 시간", example = "2021-12-31 12:00")
	LocalDateTime dateTime,

	@Schema(description = "대댓글 목록")
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
