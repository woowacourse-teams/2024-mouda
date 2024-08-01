package mouda.backend.comment.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.comment.exception.CommentException;
import mouda.backend.fixture.CommentFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;

class CommentTest {

	@DisplayName("댓글 객체를 정상적으로 생성한다.")
	@Test
	void createMoim() {
		Assertions.assertDoesNotThrow(CommentFixture::getCommentWithAnnaAtSoccerMoim);
	}

	@DisplayName("내용이 null이면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenContentIsNull() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content(null)
			.moim(MoimFixture.getBasketballMoim())
			.member(MemberFixture.getHogee())
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}

	@DisplayName("내용이 빈 문자열이면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenContentDoesNotExist() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content("")
			.moim(MoimFixture.getBasketballMoim())
			.member(MemberFixture.getHogee())
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}

	@DisplayName("모임이 존재하지 않으면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenMoimDoesNotExist() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content("댓글댓글")
			.moim(null)
			.member(MemberFixture.getHogee())
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}

	@DisplayName("멤버가 존재하지 않으면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenMemberDoesNotExist() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content("댓글댓글")
			.moim(MoimFixture.getBasketballMoim())
			.member(null)
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}
}
