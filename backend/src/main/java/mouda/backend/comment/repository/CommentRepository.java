package mouda.backend.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c.member.member.id FROM Comment c WHERE c.parentId = :parentId")
	Long findMemberIdByParentId(@Param("parentId") long parentId);

	List<Comment> findAllByMoimIdOrderByCreatedAt(long id);
}
