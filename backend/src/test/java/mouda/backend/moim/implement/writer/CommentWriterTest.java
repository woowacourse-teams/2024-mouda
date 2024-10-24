package mouda.backend.moim.implement.writer;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.CommentFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.CommentErrorMessage;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class CommentWriterTest extends DarakbangSetUp {

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private CommentWriter commentWriter;

	@Autowired
	private CommentRepository commentRepository;

	@DisplayName("댓글을 추가한다.")
	@Test
	void saveComment() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		assertThatCode(() -> commentWriter.saveComment(moim, darakbangHogee, null, "댓글"))
			.doesNotThrowAnyException();
	}

	@DisplayName("답글을 추가한다.")
	@Test
	void saveReply() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Comment parentComment = commentRepository.save(CommentFixture.getCommentWithAnnaAtSoccerMoim(darakbangHogee, moim));

		assertThatCode(() -> commentWriter.saveComment(moim, darakbangHogee, parentComment.getId(), "답글"))
			.doesNotThrowAnyException();
	}

	@DisplayName("답글을 추가할때 부모 댓글이 없으면 예외가 발생한다.")
	@Test
	void saveReply_whenParentCommentNotExist() {
		Moim moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));

		assertThatThrownBy(() -> commentWriter.saveComment(moim, darakbangHogee, 1L, "답글"))
			.isInstanceOf(CommentException.class)
			.hasMessage(CommentErrorMessage.PARENT_NOT_FOUND.getMessage());
	}
}
