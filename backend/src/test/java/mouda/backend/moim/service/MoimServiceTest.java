package mouda.backend.moim.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.comment.domain.Comment;
import mouda.backend.comment.dto.request.CommentCreateRequest;
import mouda.backend.comment.exception.CommentException;
import mouda.backend.comment.repository.CommentRepository;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class MoimServiceTest {

	@Autowired
	private MoimService moimService;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("모임을 생성한다.")
	@Test
	void createMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);

		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Moim moim = moimService.createMoim(moimCreateRequest, hogee);

		assertThat(moim.getId()).isEqualTo(1L);
	}

	@DisplayName("모임을 전체 조회한다.")
	@Test
	void findAllMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);

		Member hogee = memberRepository.save(MemberFixture.getHogee());
		moimService.createMoim(moimCreateRequest, hogee);
		moimService.createMoim(moimCreateRequest, hogee);

		MoimFindAllResponses moimResponses = moimService.findAllMoim();

		assertThat(moimResponses.moims()).hasSize(2);
	}

	@DisplayName("모임 상세를 조회한다.")
	@Test
	void findMoimDetails() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		moimService.createMoim(moimCreateRequest, hogee);

		MoimDetailsFindResponse moimDetails = moimService.findMoimDetails(1L);

		assertThat(moimDetails.title()).isEqualTo("title");
	}

	@DisplayName("모임을 삭제한다.")
	@Test
	void deleteMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "설명"
		);
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Moim moim = moimService.createMoim(moimCreateRequest, hogee);

		moimService.deleteMoim(moim.getId(), hogee);

		List<Moim> moims = moimRepository.findAll();
		assertThat(moims).hasSize(0);
	}

	@DisplayName("댓글을 생성한다.")
	@Test
	void createComment() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim());
		Member member = memberRepository.save(MemberFixture.getAnna());

		CommentCreateRequest commentCreateRequest = new CommentCreateRequest(null, "댓글부대");
		moimService.createComment(member, moim.getId(), commentCreateRequest);

		List<Comment> comments = commentRepository.findAllByMoimIdOrderByCreatedAt(moim.getId());
		assertThat(comments).hasSize(1);
	}

	@DisplayName("부모 댓글이 없이 대댓글을 생성 시 예외가 발생한다.")
	@Test
	void failToCreateChildCommentWhenParentCommentDoesNotExist() {
		Moim moim = moimRepository.save(MoimFixture.getBasketballMoim());
		Member member = memberRepository.save(MemberFixture.getAnna());

		CommentCreateRequest commentCreateRequest = new CommentCreateRequest(1L, "댓글부대");

		assertThrows(CommentException.class,
			() -> moimService.createComment(member, moim.getId(), commentCreateRequest));
	}
}
