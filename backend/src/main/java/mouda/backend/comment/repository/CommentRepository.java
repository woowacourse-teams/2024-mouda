package mouda.backend.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByMoimIdOrderByCreatedAt(long id);
}
