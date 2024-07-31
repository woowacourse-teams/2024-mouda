package mouda.backend.moim.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.exception.MoimException;
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
			10, "안나", "설명"
		);

		Moim moim = moimService.createMoim(moimCreateRequest);

		assertThat(moim.getId()).isEqualTo(1L);
	}

	@DisplayName("모임을 전체 조회한다.")
	@Test
	void findAllMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "안나", "설명"
		);
		moimService.createMoim(moimCreateRequest);
		moimService.createMoim(moimCreateRequest);

		MoimFindAllResponses moimResponses = moimService.findAllMoim();

		assertThat(moimResponses.moims()).hasSize(2);
	}

	@DisplayName("모임 상세를 조회한다.")
	@Test
	void findMoimDetails() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "안나", "설명"
		);
		moimService.createMoim(moimCreateRequest);

		MoimDetailsFindResponse moimDetails = moimService.findMoimDetails(1L);

		assertThat(moimDetails.title()).isEqualTo("title");
	}

	@DisplayName("모임에 참여한다.")
	@Test
	void joinMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "안나", "설명"
		);
		Moim moim = moimService.createMoim(moimCreateRequest);

		MoimJoinRequest moimJoinRequest = new MoimJoinRequest(moim.getId(), "호기");
		moimService.joinMoim(moimJoinRequest);
		List<Member> participants = memberRepository.findAllByMoimId(moim.getId());

		Optional<Moim> moimOptional = moimRepository.findById(moim.getId());
		assertThat(moimOptional).isNotEmpty();
		assertThat(participants.size()).isEqualTo(2);
	}

	@DisplayName("모임을 삭제한다.")
	@Test
	void deleteMoim() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now().plusDays(1), LocalTime.now(), "place",
			10, "안나", "설명"
		);
		moimService.createMoim(moimCreateRequest);

		moimService.deleteMoim(1L);

		List<Moim> moims = moimRepository.findAll();
		assertThat(moims).hasSize(0);
	}

	@DisplayName("최대 참여 인원을 넘으면 예외가 발생한다.")
	@Test
	void failToJoinMoimWhenExceedMaxPeople() {
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			"title", LocalDate.now(), LocalTime.now(), "place",
			2, "안나", "설명"
		);

		Moim moim = moimService.createMoim(moimCreateRequest);
		MoimJoinRequest hogee = new MoimJoinRequest(moim.getId(), "호기");
		moimService.joinMoim(hogee);
		MoimJoinRequest tebah = new MoimJoinRequest(moim.getId(), "테바");

		assertThrows(MoimException.class, () -> moimService.joinMoim(tebah));
	}
}
