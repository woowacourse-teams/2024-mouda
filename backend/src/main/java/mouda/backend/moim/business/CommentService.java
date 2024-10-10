package mouda.backend.moim.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.sender.CommentNotificationSender;
import mouda.backend.moim.implement.writer.CommentWriter;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.notification.domain.NotificationType;

//todo: 댓글 작성시 알림 전송 코드 추가하기
//todo: parentId가 null인지에 따라 댓글인지 / 답글인지 확인 가능
@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {

	private final MoimFinder moimFinder;
	private final CommentWriter commentWriter;
	private final CommentNotificationSender commentNotificationSender;

	public void createComment(
		Long darakbangId, Long moimId, DarakbangMember darakbangMember, CommentCreateRequest request
	) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		Comment comment = commentWriter.saveComment(moim, darakbangMember, request.parentId(), request.content());

		commentNotificationSender.sendCommentNotification(comment, darakbangMember);
	}
}
