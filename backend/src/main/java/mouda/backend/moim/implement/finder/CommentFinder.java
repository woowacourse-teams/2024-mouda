package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.ParentComment;
import mouda.backend.moim.infrastructure.CommentRepository;

@Component
@RequiredArgsConstructor
public class CommentFinder {

	private final CommentRepository commentRepository;

	public List<ParentComment> readAllParentComments(Moim moim) {
		List<Comment> comments = commentRepository.findAllByMoimOrderByCreatedAt(moim);

		return comments.stream()
			.filter(Comment::isParent)
			.map(parentComment -> new ParentComment(parentComment, getChildComments(parentComment, comments)))
			.collect(Collectors.toList());
	}

	private List<Comment> getChildComments(Comment parentComment, List<Comment> comments) {
		return comments.stream()
			.filter(comment -> Objects.equals(comment.getParentId(), parentComment.getId()))
			.toList();
	}

	public Long readMemberIdByParentId(Long parentId) {
		return commentRepository.findMemberIdByParentId(parentId);
	}

	public Optional<Comment> findByParentId(Long parentId) {
		if (parentId == null) {
			return Optional.empty();
		}

		return commentRepository.findByParentId(parentId);
	}
}
