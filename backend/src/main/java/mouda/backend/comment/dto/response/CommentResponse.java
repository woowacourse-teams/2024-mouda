package mouda.backend.comment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import mouda.backend.comment.domain.Comment;

@Builder
public record CommentResponse(
	Long commentId,
	String nickname,
	String content,
	LocalDateTime dateTime,
	List<ChildCommentResponse> childs
) {
	public static CommentResponse toResponse(Comment parentComment, List<ChildCommentResponse> childComments) {
		return CommentResponse.builder()
			.commentId(parentComment.getId())
			.nickname(parentComment.getMember().getNickname())
			.content(parentComment.getContent())
			.dateTime(parentComment.getCreatedAt())
			.childs(childComments)
			.build();
	}
}
