package mouda.backend.moim.implement.finder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class CommentRecipientFinder {

	private final ChamyoRepository chamyoRepository;
	private final CommentRepository commentRepository;

	public List<Recipient> getNewCommentNotificationRecipients(long moimId, DarakbangMember commentAuthor) {
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moimId);
		return chamyos.stream()
			.filter(chamyo -> chamyo.getDarakbangMember().getId() != commentAuthor.getId())
			.map(chamyo -> new Recipient(chamyo.getDarakbangMember().getMemberId()))
			.toList();
	}

	public List<Recipient> getNewReplyNotificationRecipients(Comment parentComment) {
		Recipient recipient = new Recipient(parentComment.getDarakbangMember().getMemberId());
		List<Recipient> recipients = new ArrayList<>();
		recipients.add(recipient);
		return recipients;
	}
}
