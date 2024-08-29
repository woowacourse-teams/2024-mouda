package mouda.backend.comment.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.comment.domain.Comment;

@Builder
@Schema(name = "대댓글 조회 응답", description = "댓글의 대댓글을 조회할 때 사용")
public record ChildCommentResponse(
	@Schema(description = "대댓글 ID", example = "1")
	Long commentId,

	@Schema(description = "대댓글 작성자 닉네임", example = "테니")
	String nickname,

	@Schema(description = "대댓글 내용", example = "댓글 내용")
	String content,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Schema(description = "대댓글 작성 시간", example = "2021-12-31 12:00")
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
