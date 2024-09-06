package mouda.backend.core.dto.moim.request.comment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Comment;
import mouda.backend.core.domain.moim.Moim;

public record CommentCreateRequest(
	Long parentId,

	@NotNull
	String content
) {
	public Comment toEntity(Moim moim, DarakbangMember darakbangMember) {
		return Comment.builder()
			.content(content)
			.moim(moim)
			.darakbangMember(darakbangMember)
			.createdAt(LocalDateTime.now())
			.parentId(parentId)
			.build();
	}
}
