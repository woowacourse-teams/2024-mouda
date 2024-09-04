package mouda.backend.moim.moim.business;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.global.IgnoreNotificationTest;
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
import mouda.backend.moim.business.MoimService;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimCreateRequest;
import mouda.backend.moim.presentation.response.moim.MoimDetailsFindResponse;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponses;

@SpringBootTest
class MoimServiceTest extends IgnoreNotificationTest {

	@Autowired
	private MoimService moimService;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CommentRepository commentRepository;

	private Darakbang darakbang;
	private Darakbang mouda;
	private DarakbangMember darakbangHogee;
	private DarakbangMember moudaHogee;

	@BeforeEach
	void setUp() {
		darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		darakbangHogee = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));
		moudaHogee = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));
	}

	@DisplayName("모임을 생성한다.")
	@Test
	void createMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);

		Moim moim = moimService.createMoim(darakbang.getId(), darakbangHogee, moimCreateRequest);

		assertThat(moim.getId()).isEqualTo(1L);
	}

	@DisplayName("모임을 전체 조회한다.")
	@Test
	void findAllMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);

		moimService.createMoim(darakbang.getId(), darakbangHogee, moimCreateRequest);
		moimService.createMoim(darakbang.getId(), darakbangHogee, moimCreateRequest);

		MoimFindAllResponses moimResponses = moimService.findAllMoim(darakbang.getId(), darakbangHogee);

		assertThat(moimResponses.moims()).hasSize(2);
	}

	@DisplayName("모임 상세를 조회한다.")
	@Test
	void findMoimDetails() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);
		moimService.createMoim(darakbang.getId(), darakbangHogee, moimCreateRequest);

		MoimDetailsFindResponse moimDetails = moimService.findMoimDetails(darakbang.getId(), 1L);

		assertThat(moimDetails.title()).isEqualTo("title");
	}

	@DisplayName("모임을 삭제한다.")
	@Test
	void deleteMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);
		Moim moim = moimService.createMoim(darakbang.getId(), darakbangHogee, moimCreateRequest);

		moimService.deleteMoim(darakbang.getId(), moim.getId(), darakbangHogee);

		List<Moim> moims = moimRepository.findAll();
		assertThat(moims).hasSize(0);
	}

	@DisplayName("댓글을 생성한다.")
	@Test
	void createComment() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		CommentCreateRequest commentCreateRequest = new CommentCreateRequest(null, "댓글부대");
		moimService.createComment(darakbang.getId(), moim.getId(), darakbangHogee, commentCreateRequest);

		List<Comment> comments = commentRepository.findAllByMoimIdOrderByCreatedAt(moim.getId());
		assertThat(comments).hasSize(1);
	}

	@DisplayName("부모 댓글이 없이 대댓글을 생성 시 예외가 발생한다.")
	@Test
	void failToCreateChildCommentWhenParentCommentDoesNotExist() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));
		CommentCreateRequest commentCreateRequest = new CommentCreateRequest(1L, "댓글부대");

		assertThrows(CommentException.class,
			() -> moimService.createComment(darakbang.getId(), moim.getId(), darakbangHogee, commentCreateRequest));
	}

	@DisplayName("다락방별 모임을 조회한다.")
	@Test
	void success() {
		moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));

		assertThat(moimService.findAllMoim(darakbang.getId(), darakbangHogee).moims()).hasSize(1);
		assertThat(moimService.findAllMoim(mouda.getId(), moudaHogee).moims()).hasSize(0);
	}
}
