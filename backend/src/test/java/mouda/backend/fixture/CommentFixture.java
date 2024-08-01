package mouda.backend.fixture;

import java.time.LocalDateTime;

import mouda.backend.comment.domain.Comment;

public class CommentFixture {

	public static Comment getCommentWithAnnaAtSoccerMoim() {
		return Comment.builder()
			.content("그냥 댓글")
			.moim(MoimFixture.getSoccerMoim())
			.member(MemberFixture.getAnna())
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build();
	}

	public static Comment getChildCommentWithAnnaAtSoccerMoim() {
		return Comment.builder()
			.content("그냥 자식 댓글")
			.moim(MoimFixture.getSoccerMoim())
			.member(MemberFixture.getAnna())
			.createdAt(LocalDateTime.now())
			.parentId(1L)
			.build();
	}
}
