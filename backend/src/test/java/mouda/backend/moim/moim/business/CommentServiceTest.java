package mouda.backend.moim.moim.business;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;
import mouda.backend.moim.business.CommentService;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	private Darakbang darakbang;
	private DarakbangMember darakbangHogee;

	@BeforeEach
	void setUp() {
		darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		darakbangHogee = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));
	}

	@DisplayName("댓글을 생성한다.")
	@Test
	void createComment() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		CommentCreateRequest commentCreateRequest = new CommentCreateRequest(null, "댓글부대");
		commentService.createComment(darakbang.getId(), moim.getId(), darakbangHogee, commentCreateRequest);

		List<Comment> comments = commentRepository.findAllByMoimOrderByCreatedAt(moim);
		assertThat(comments).hasSize(1);
	}

	@DisplayName("부모 댓글이 없이 대댓글을 생성 시 예외가 발생한다.")
	@Test
	void failToCreateChildCommentWhenParentCommentDoesNotExist() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		CommentCreateRequest commentCreateRequest = new CommentCreateRequest(1L, "댓글부대");

		assertThrows(CommentException.class,
			() -> commentService.createComment(darakbang.getId(), moim.getId(), darakbangHogee, commentCreateRequest));
	}
}
