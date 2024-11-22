package mouda.backend.moim.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c.darakbangMember.memberId FROM Comment c WHERE c.id = :parentId")
	Long findMemberIdByParentId(@Param("parentId") long parentId);

	List<Comment> findAllByMoimOrderByCreatedAt(Moim moim);

	@Query("SELECT c FROM Comment c WHERE c.id = :parentId")
	Optional<Comment> findParentCommentByParentId(@Param("parentId") Long parentId);
}
