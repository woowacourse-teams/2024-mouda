package mouda.backend.common.fixture;

import java.time.LocalDateTime;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;

public class CommentFixture {

	public static Comment getCommentWithAnnaAtSoccerMoim(DarakbangMember darakbangMember, Moim moim) {
		return Comment.builder()
			.content("그냥 댓글")
			.moim(moim)
			.darakbangMember(darakbangMember)
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build();
	}

	public static Comment getChildCommentWithAnnaAtSoccerMoim(DarakbangMember darakbangMember, Moim moim) {
		return Comment.builder()
			.content("그냥 자식 댓글")
			.moim(moim)
			.darakbangMember(darakbangMember)
			.createdAt(LocalDateTime.now())
			.parentId(1L)
			.build();
	}
}
