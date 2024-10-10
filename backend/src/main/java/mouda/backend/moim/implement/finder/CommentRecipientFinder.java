package mouda.backend.moim.implement.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.CommentRecipient;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.ChamyoErrorMessage;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.exception.CommentErrorMessage;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class CommentRecipientFinder {

	private final ChamyoRepository chamyoRepository;
	private final CommentRepository commentRepository;

	public List<CommentRecipient> getAllRecipient(Comment comment) {
		if (comment.isComment()) {
			return getCommentRecipientWhenComment(comment);
		}
		return getCommentRecipientWhenReply(comment);
	}

	private List<CommentRecipient> getCommentRecipientWhenComment(Comment comment) {
		List<CommentRecipient> result = new ArrayList<>();
		Moim moim = comment.getMoim();
		DarakbangMember moimer = getMoimer(moim);
		DarakbangMember author = comment.getDarakbangMember();

		if (!Objects.equals(moimer.getId(), author.getId())) {
			result.add(new CommentRecipient(NotificationType.NEW_COMMENT,
				List.of(new Recipient(moimer.getMemberId(), moimer.getId()))));
		}

		return result;
	}


	// 답글
	// 작성자가 방장인 경우:
	// 			-> 원 댓글 작성자가 방장이면 아무에게도 알림 X
	// 			-> 원 댓글 작성자가 방장이 아니면 원 댓글 작성자에게만 답글 알림
	// 작성자가 방장이 아닌 경우:
	// 			-> 원 댓글 작성자가 방장인 경우: 방장에게 답글 알림
	// 			-> 원 댓글 작성자가 방장이 아닌 경우: 원 댓글 작성자에게는 답글 알림, 방장에게는 댓글 알림.
	private List<CommentRecipient> getCommentRecipientWhenReply(Comment comment) {
		List<CommentRecipient> result = new ArrayList<>();
		Moim moim = comment.getMoim();
		DarakbangMember moimer = getMoimer(moim);
		DarakbangMember author = comment.getDarakbangMember();
		DarakbangMember parentAuthor = commentRepository.findParentCommentByParentId(comment.getParentId())
			.map(Comment::getDarakbangMember)
			.orElseThrow(() -> new CommentException(HttpStatus.NOT_FOUND, CommentErrorMessage.PARENT_NOT_FOUND));

		// 작성자가 방장인 경우
		if (Objects.equals(author.getId(), moimer.getId())) {
			// 원 댓글 작성자가 방장이 아닌 경우
			if (!Objects.equals(parentAuthor.getId(), moimer.getId())) {
				result.add(new CommentRecipient(NotificationType.NEW_REPLY,
					List.of(new Recipient(parentAuthor.getMemberId(), parentAuthor.getId()))));
			}
		} else {
			// 작성자가 방장이 아닌 경우
			if (Objects.equals(parentAuthor.getId(), moimer.getId())) {
				// 원 댓글 작성자가 방장인 경우
				result.add(new CommentRecipient(NotificationType.NEW_REPLY,
					List.of(new Recipient(moimer.getMemberId(), moimer.getId()))));
			} else {
				// 원 댓글 작성자가 방장이 아닌 경우
				result.add(new CommentRecipient(NotificationType.NEW_REPLY,
					List.of(new Recipient(parentAuthor.getMemberId(), parentAuthor.getId()))));
				result.add(new CommentRecipient(NotificationType.NEW_COMMENT,
					List.of(new Recipient(moimer.getMemberId(), moimer.getId()))));
			}
		}

		return result;
	}

	private DarakbangMember getMoimer(Moim moim) {
		Optional<Chamyo> moimerChamyo = chamyoRepository.findMoimerByMoimId(moim.getId());
		if (moimerChamyo.isEmpty()) {
			throw new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.NOT_FOUND);
		}
		return moimerChamyo.get().getDarakbangMember();
	}
}
