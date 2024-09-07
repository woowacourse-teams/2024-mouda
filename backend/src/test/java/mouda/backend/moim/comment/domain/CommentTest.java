package mouda.backend.moim.comment.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.common.fixture.CommentFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.exception.CommentException;

class CommentTest extends DarakbangSetUp {

	@DisplayName("댓글 객체를 정상적으로 생성한다.")
	@Test
	void createMoim() {
		Assertions.assertDoesNotThrow(() -> CommentFixture.getCommentWithAnnaAtSoccerMoim(
			darakbangAnna, MoimFixture.getSoccerMoim(darakbang.getId()))
		);
	}

	@DisplayName("내용이 null이면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenContentIsNull() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content(null)
			.moim(MoimFixture.getBasketballMoim(darakbang.getId()))
			.darakbangMember(darakbangAnna)
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}

	@DisplayName("내용이 빈 문자열이면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenContentDoesNotExist() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content("")
			.moim(MoimFixture.getBasketballMoim(darakbang.getId()))
			.darakbangMember(darakbangAnna)
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
			.darakbangMember(darakbangAnna)
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}

	@DisplayName("멤버가 존재하지 않으면 댓글 객체 생성에 실패한다.")
	@Test
	void failToCreateCommentWhenMemberDoesNotExist() {
		assertThrows(CommentException.class, () -> Comment.builder()
			.content("댓글댓글")
			.moim(MoimFixture.getBasketballMoim(darakbang.getId()))
			.darakbangMember(null)
			.createdAt(LocalDateTime.now())
			.parentId(null)
			.build());
	}
}
