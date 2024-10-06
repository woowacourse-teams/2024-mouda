package mouda.backend.moim.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.event.BaseNotificationEvent;
import mouda.backend.moim.domain.event.ChamyoEvent;
import mouda.backend.moim.domain.event.CommentEvent;
import mouda.backend.moim.domain.event.MoimEvent;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.CommentFinder;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.NotificationType;

//todo: 여기 있는 로직을 NotificationSender로 이전해야함
@Component
@RequiredArgsConstructor
public class MoimEventListener {

	private final NotificationService notificationService;
	private final DarakbangFinder darakbangFinder;
	private final ChamyoFinder chamyoFinder;
	private final CommentFinder commentFinder;

	@TransactionalEventListener(value = MoimEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(MoimEvent event) {
		Moim moim = event.getMoim();
		CommonNotification notification = createNotification(event, moim.getTitle());

		Predicate<Chamyo> filter = chamyo -> chamyo.getMoimRole() == MoimRole.MOIMEE;
		List<DarakbangMember> receivers = getReceivers(moim, filter);

		notificationService.sendNotification(notification, receivers);
	}

	@TransactionalEventListener(value = ChamyoEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(ChamyoEvent event) {
		DarakbangMember updatedMember = event.getUpdatedMember();
		CommonNotification notification = createNotification(event, updatedMember.getNickname());

		Predicate<Chamyo> filter = c -> !updatedMember.equals(c.getDarakbangMember());
		List<DarakbangMember> receivers = getReceivers(event.getMoim(), filter);

		notificationService.sendNotification(notification, receivers);
	}

	@TransactionalEventListener(value = CommentEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(CommentEvent event) {
		Moim moim = event.getMoim();

		DarakbangMember author = event.getAuthor();
		CommonNotification notification = createNotification(event, author.getNickname());
		List<DarakbangMember> receivers = getCommentReceivers(moim, author, event.getParentCommentId());

		notificationService.sendNotification(notification, receivers);
	}

	private List<DarakbangMember> getReceivers(Moim moim, Predicate<Chamyo> filter) {
		return chamyoFinder.readAll(moim).stream()
			.filter(filter)
			.map(Chamyo::getDarakbangMember)
			.toList();
	}

	private List<DarakbangMember> getCommentReceivers(Moim moim, DarakbangMember author, Long parentCommentId) {
		List<DarakbangMember> result = new ArrayList<>();

		boolean isAuthorNotMoimer = chamyoFinder.readMoimRole(moim, author) != MoimRole.MOIMER;
		if (isAuthorNotMoimer) {
			result.add(author);
		}

		Optional<Comment> parentComment = commentFinder.findByParentId(parentCommentId);
		if (parentComment.isPresent()) {
			DarakbangMember parentCommentAuthor = parentComment.get().getDarakbangMember();
			result.add(parentCommentAuthor);
		}

		return result;
	}

	private CommonNotification createNotification(BaseNotificationEvent event, String messageValue) {
		Moim moim = event.getMoim();
		Darakbang darakbang = darakbangFinder.findById(moim.getDarakbangId());
		NotificationType type = event.getNotificationType();

		return CommonNotification.builder()
			.type(type)
			.title(darakbang.getName())
			.body(type.createMessage(messageValue))
			.redirectUrl(event.getRedirectUrl(darakbang.getId(), moim.getId()))
			.build();
	}
}
