package mouda.backend.common.fixture;

import java.time.LocalDateTime;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Comment;
import mouda.backend.core.domain.moim.Moim;

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
