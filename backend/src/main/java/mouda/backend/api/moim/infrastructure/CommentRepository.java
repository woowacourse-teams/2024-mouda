package mouda.backend.api.moim.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.core.domain.moim.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c.darakbangMember.memberId FROM Comment c WHERE c.id = :parentId")
	Long findMemberIdByParentId(@Param("parentId") long parentId);

	List<Comment> findAllByMoimIdOrderByCreatedAt(long id);
}
