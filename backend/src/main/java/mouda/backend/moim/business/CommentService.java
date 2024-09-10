package mouda.backend.moim.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.CommentFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.writer.CommentWriter;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {

	private final MoimFinder moimFinder;
	private final ChamyoFinder chamyoFinder;
	private final CommentFinder commentFinder;
	private final CommentWriter commentWriter;
	private final NotificationService notificationService;

	public void createComment(
		Long darakbangId, Long moimId, DarakbangMember darakbangMember, CommentCreateRequest request
	) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		commentWriter.saveComment(moim, darakbangMember, request.parentId(), request.content());

		sendCommentNotification(moim, darakbangMember, request.parentId(), darakbangId);
	}

	private void sendCommentNotification(Moim moim, DarakbangMember author, Long parentId, Long darakbangId) {
		if (chamyoFinder.readMoimRole(moim, author) == MoimRole.MOIMER) {
			return;
		}
		if (parentId != null) {
			Long parentCommentAuthorId = commentFinder.readMemberIdByParentId(parentId);
			notificationService.notifyToMember(NotificationType.NEW_REPLY, darakbangId, moim, author,
				parentCommentAuthorId);
		}

		notificationService.notifyToMembers(NotificationType.NEW_COMMENT, darakbangId, moim, author);
	}
}
