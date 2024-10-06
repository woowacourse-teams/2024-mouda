package mouda.backend.moim.domain.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@SuperBuilder
public class CommentEvent extends BaseNotificationEvent {

	private final DarakbangMember author;
	private final Long parentCommentId;
	private final String specificUrl;

	public CommentEvent(String baseUrl, String specificUrl, Moim moim, NotificationType notificationType, DarakbangMember author,
		Long parentCommentId) {
		super(baseUrl, moim, notificationType);
		this.author = author;
		this.parentCommentId = parentCommentId;
		this.specificUrl = specificUrl;
	}

	@Override
	public String getSpecificUrl() {
		return specificUrl;
	}
}
