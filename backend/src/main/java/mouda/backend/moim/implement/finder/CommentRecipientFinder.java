package mouda.backend.moim.implement.finder;

import java.util.ArrayList;
import java.util.List;
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

	// 댓글
	// 작성자가 방장인 경우: 아무에게도 알림을 보내지 않음.
	// 작성자가 방장이 아닌 경우: 방장에게 '댓글' 알림을 보냄.
	private List<CommentRecipient> getCommentRecipientWhenComment(Comment comment) {
		List<CommentRecipient> result = new ArrayList<>();
		Moim moim = comment.getMoim();
		DarakbangMember moimer = getMoimer(moim);
		DarakbangMember author = comment.getDarakbangMember();

		if (moimer.isNotSameMemberWith(author)) {
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
	// 			-> 원 댓글 작성자가 방장인 경우: 방장에게만 답글 알림
	//          -> 원 댓글 작성자가 자신인 경우: 방장에게만 댓글 알림
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
		if (author.isSameMemberWith(moimer)) {
			// 원 댓글 작성자가 방장이 아닌 경우
			if (parentAuthor.isNotSameMemberWith(moimer)) {
				result.add(new CommentRecipient(NotificationType.NEW_REPLY,
					List.of(new Recipient(parentAuthor.getMemberId(), parentAuthor.getId()))));
			}
		} else {
			// 작성자가 방장이 아닌 경우
			if (parentAuthor.isSameMemberWith(moimer)) {
				// 원 댓글 작성자가 방장인 경우
				result.add(new CommentRecipient(NotificationType.NEW_REPLY,
					List.of(new Recipient(moimer.getMemberId(), moimer.getId()))));
			} else {
				// 원 댓글 작성자가 방장이 아닌 경우
				if (parentAuthor.isNotSameMemberWith(author)) {
					result.add(new CommentRecipient(NotificationType.NEW_REPLY,
						List.of(new Recipient(parentAuthor.getMemberId(), parentAuthor.getId()))));
				}
				result.add(new CommentRecipient(NotificationType.NEW_COMMENT,
					List.of(new Recipient(moimer.getMemberId(), moimer.getId()))));
			}
		}

		return result;
	}

	private DarakbangMember getMoimer(Moim moim) {
		Optional<Chamyo> chamyoOptional = chamyoRepository.findMoimerByMoimId(moim.getId());
		if (chamyoOptional.isEmpty()) {
			throw new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.NOT_FOUND);
		}
		return chamyoOptional.get().getDarakbangMember();
	}
}
