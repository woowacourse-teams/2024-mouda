package mouda.backend.moim.implement.notificiation.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;

@Getter
@RequiredArgsConstructor
@Builder
public class CommentNotificationEvent {

	private final Comment comment;
	private final DarakbangMember author;
}
