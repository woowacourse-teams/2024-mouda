package mouda.backend.moim.implement.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.exception.CommentErrorMessage;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.infrastructure.CommentRepository;

@Component
@RequiredArgsConstructor
public class CommentValidator {

	private final CommentRepository commentRepository;

	public void validateParentCommentExists(Long parentId) {
		if (parentId == null) {
			return;
		}
		if (!commentRepository.existsById(parentId)) {
			throw new CommentException(HttpStatus.BAD_REQUEST, CommentErrorMessage.PARENT_NOT_FOUND);
		}
	}
}
