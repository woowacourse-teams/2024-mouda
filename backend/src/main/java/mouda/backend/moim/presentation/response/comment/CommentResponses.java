package mouda.backend.moim.presentation.response.comment;

import java.util.List;

import mouda.backend.moim.domain.ParentComment;

public record CommentResponses(
	List<CommentResponse> commentResponses
) {

	public static CommentResponses toResponse(List<ParentComment> parentComments) {
		List<CommentResponse> responses = parentComments.stream()
			.map(CommentResponse::fromParentComment)
			.toList();

		return new CommentResponses(responses);
	}
}
