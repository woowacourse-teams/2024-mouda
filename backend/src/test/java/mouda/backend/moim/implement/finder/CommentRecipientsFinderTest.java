package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.CommentRecipients;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentRecipientsFinderTest extends DarakbangSetUp {

	@Autowired
	private CommentRecipientFinder commentRecipientFinder;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private MoimWriter moimWriter;

	private Moim moim;

	@BeforeEach
	void init() {
		moim = moimWriter.save(MoimFixture.getCoffeeMoim(darakbang.getId()), darakbangAnna);
	}

	@DisplayName("댓글인 경우")
	@Nested
	class CommentTest {

		@DisplayName("댓글 작성자가 방장이면 아무에게도 알림을 보내지 않는다.")
		@Test
		void getNewCommentNotificationRecipients_WhenAuthorMoimer() {
			Comment comment = createComment(darakbangAnna, null);

			assertThat(commentRecipientFinder.getAllRecipient(comment).getRecipients()).isEmpty();
		}

		@DisplayName("댓글 작성자가 방장이 아니면 방장에게 알림을 보낸다.")
		@Test
		void getNewCommentNotificationRecipients_WhenAuthorNotMoimer() {
			Comment comment = createComment(darakbangHogee, null);

			CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(comment);
			Map<NotificationType, List<Recipient>> results = commentRecipients.getRecipients();

			assertThat(results).hasSize(1);
			assertThat(results.keySet()).containsExactly(NotificationType.NEW_COMMENT);
			assertThat(results.get(NotificationType.NEW_COMMENT)).extracting("memberId")
				.containsExactly(darakbangAnna.getMemberId());
		}
	}

	@DisplayName("답글인 경우")
	@Nested
	class ReplyTest {

		@DisplayName("방장의 댓글에 답글을 남기는 경우")
		@Nested
		class ReplyToMoimer {

			private Comment parentComment;

			@BeforeEach
			void setUp() {
				parentComment = createComment(darakbangAnna, null);
			}

			@DisplayName("방장 자신이 답글을 남기는 경우 아무에게도 알림을 보내지 않는다.")
			@Test
			void getReplyNotificationRecipients_WhenAuthorMoimer() {
				Comment childComment = createComment(darakbangAnna, parentComment.getId());

				CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(childComment);
				Map<NotificationType, List<Recipient>> results = commentRecipients.getRecipients();

				assertThat(results).isEmpty();
			}

			@DisplayName("방장 이외의 사람이 답글을 남기는 경우 방장에게 '답글' 알림을 보낸다.")
			@Test
			void getReplyNotificationRecipients_WhenAuthorNotMoimer() {
				Comment childComment = createComment(darakbangHogee, parentComment.getId());

				CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(childComment);
				Map<NotificationType, List<Recipient>> results = commentRecipients.getRecipients();

				assertThat(results).hasSize(1);
				assertThat(results.keySet()).containsExactly(NotificationType.NEW_REPLY);
				assertThat(results.get(NotificationType.NEW_REPLY)).extracting("memberId")
					.containsExactly(darakbangAnna.getMemberId());
			}
		}

		@DisplayName("방장이 아닌 사람의 댓글에 답글을 남기는 경우")
		@Nested
		class ReplyToNotMoimer {

			private Comment parentComment;

			@BeforeEach
			void setUp() {
				parentComment = createComment(darakbangHogee, null);
			}

			@DisplayName("방장이 답글을 남기면 댓글 작성자에게만 알림을 보낸다.")
			@Test
			void getReplyNotificationRecipients_WhenAuthorMoimer() {
				Comment childComment = createComment(darakbangAnna, parentComment.getId());

				CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(childComment);
				Map<NotificationType, List<Recipient>> results = commentRecipients.getRecipients();

				assertThat(results).hasSize(1);
				assertThat(results.keySet()).containsExactly(NotificationType.NEW_REPLY);
				assertThat(results.get(NotificationType.NEW_REPLY)).extracting("memberId")
					.containsExactly(darakbangHogee.getMemberId());

			}

			@DisplayName("방장 이외의 사람이 답글을 남기는 경우 방장에겐 '댓글' 알림을 보내고, 원 댓글 작성자에겐 '답글' 알림을 보낸다.")
			@Test
			void getReplyNotificationRecipients_WhenAuthorNotMoimer() {
				Comment childComment = createComment(darakbangManager, parentComment.getId());

				CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(childComment);
				Map<NotificationType, List<Recipient>> results = commentRecipients.getRecipients();

				assertThat(results).hasSize(2);
				assertThat(results.keySet()).containsExactlyInAnyOrder(NotificationType.NEW_COMMENT, NotificationType.NEW_REPLY);
				assertThat(results.get(NotificationType.NEW_COMMENT)).extracting("memberId")
					.containsExactly(darakbangAnna.getMemberId());
				assertThat(results.get(NotificationType.NEW_REPLY)).extracting("memberId")
					.containsExactly(darakbangHogee.getMemberId());
			}

			@DisplayName("댓글 작성자가 자신에게 답글을 남기면 방장에게만 '댓글' 알림을 보낸다.")
			@Test
			void getReplyNotificationRecipients_WhenAuthorIsParentAuthor() {
				Comment childComment = createComment(darakbangHogee, parentComment.getId());

				CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(childComment);
				Map<NotificationType, List<Recipient>> results = commentRecipients.getRecipients();

				assertThat(results).hasSize(1);
				assertThat(results.keySet()).containsExactly(NotificationType.NEW_COMMENT);
				assertThat(results.get(NotificationType.NEW_COMMENT)).extracting("memberId")
					.containsExactly(darakbangAnna.getMemberId());
			}
		}
	}

	private Comment createComment(DarakbangMember author, Long parentId) {
		return commentRepository.save(Comment.builder()
			.content("내용")
			.moim(moim)
			.darakbangMember(author)
			.createdAt(LocalDateTime.now())
			.parentId(parentId)
			.build());
	}
}
