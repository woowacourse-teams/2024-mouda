package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.CommentFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.ParentComment;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class CommentFinderTest extends DarakbangSetUp {

	@Autowired
	private CommentFinder commentFinder;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private MoimRepository moimRepository;

	@DisplayName("모든 댓글을 조회한다.")
	@Test
	void readAllParentComments() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Comment parentComment = commentRepository.save(
			CommentFixture.getCommentWithAnnaAtSoccerMoim(darakbangHogee, moim));
		Comment childComment = commentRepository.save(
			CommentFixture.getChildCommentWithAnnaAtSoccerMoim(darakbangAnna, moim));

		List<ParentComment> parentComments = commentFinder.readAllParentComments(moim);

		assertThat(parentComments).hasSize(1);
		ParentComment actual = parentComments.get(0);
		assertThat(actual.getComment().getId()).isEqualTo(parentComment.getId());
		assertThat(actual.getChildren()).hasSize(1)
			.extracting(Comment::getId).containsOnly(childComment.getId());
	}

	@DisplayName("부모 댓글의 ID로 회원 ID를 조회한다.")
	@Test
	void readMemberIdByParentId() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Comment parentComment = commentRepository.save(
			CommentFixture.getCommentWithAnnaAtSoccerMoim(darakbangHogee, moim));
		Comment childComment = commentRepository.save(
			CommentFixture.getChildCommentWithAnnaAtSoccerMoim(darakbangAnna, moim));

		Long memberId = commentFinder.readMemberIdByParentId(childComment.getParentId());

		assertThat(memberId).isEqualTo(darakbangHogee.getId());
	}
}