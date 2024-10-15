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
