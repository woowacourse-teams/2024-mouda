package mouda.backend.moim.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.common.fixture.CommentFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.notificiation.CommentRecipientFinder;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;

@SpringBootTest
public class CommentAsyncTest extends DarakbangSetUp {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@MockBean
	private CommentRecipientFinder commentRecipientFinder;

	private Moim moim;
	private Comment parentComment;

	@BeforeEach
	void init() {
		moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		chamyoRepository.save(Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build());
		parentComment = commentRepository.save(CommentFixture.getCommentWithoutParentId(darakbangAnna, moim));
	}

	@DisplayName("댓글 생성시 알림 전송 과정에서 예외가 발생해도 댓글은 생성된다.")
	@Test
	void asyncWhenCommentCreate() {
		// given
		String content = "비동기 확인 댓글 ~";

		// when
		when(commentRecipientFinder.getAllRecipient(any(Comment.class)))
			.thenThrow(new RuntimeException("삐용12"));

		commentService.createComment(darakbang.getId(), moim.getId(), darakbangHogee, new CommentCreateRequest(
			null,
			content
		));

		// then
		Optional<Comment> commentOptional = commentRepository.findById(getCommentId(moim, content));
		assertThat(commentOptional).isNotEmpty();

		Comment comment = commentOptional.get();
		assertThat(comment.getContent()).isEqualTo(content);
	}

	@DisplayName("답글 작성시 알림 전송 과정에서 예외가 발생해도 답글은 생성된다.")
	@Test
	void asyncWhenReplyCreate() {
		// given
		String content = "비동기 확인 답글 ~";

		// when
		when(commentRecipientFinder.getAllRecipient(any(Comment.class)))
			.thenThrow(new RuntimeException("삐용12"));

		commentService.createComment(darakbang.getId(), moim.getId(), darakbangHogee, new CommentCreateRequest(
			parentComment.getId(),
			content
		));

		// then
		Optional<Comment> commentOptional = commentRepository.findById(getCommentId(moim, content));
		assertThat(commentOptional).isNotEmpty();

		Comment comment = commentOptional.get();
		assertThat(comment.getContent()).isEqualTo(content);
	}

	private long getCommentId(Moim moim, String content) {
		return commentRepository.findAllByMoimOrderByCreatedAt(moim).stream()
			.filter(comment -> comment.getContent().equals(content))
			.findFirst()
			.orElseThrow(RuntimeException::new)
			.getId();
	}
}
