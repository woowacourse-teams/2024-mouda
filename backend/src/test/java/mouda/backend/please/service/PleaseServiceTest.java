package mouda.backend.please.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.PleaseFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.InterestUpdateRequest;
import mouda.backend.please.dto.request.PleaseCreateRequest;
import mouda.backend.please.dto.response.PleaseFindAllResponses;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.PleaseRepository;

@SpringBootTest
class PleaseServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PleaseRepository pleaseRepository;

	@Autowired
	private PleaseService pleaseService;

	@Autowired
	private InterestService interestService;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("해주세요 생성 테스트")
	@Nested
	class PleaseCreateTest {

		@DisplayName("해주세요를 성공적으로 생성한다.")
		@Test
		void success() {
			PleaseCreateRequest request = new PleaseCreateRequest("치킨 사주세요", "제발요");
			Member tebah = memberRepository.save(MemberFixture.getTebah());

			Please please = pleaseService.createPlease(tebah, request);

			assertThat(please.getId()).isEqualTo(1L);
		}

		@DisplayName("해주세요의 제목이 비어있으면 해주세요 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreatePleaseWithoutTitle(String emptyTitle) {
			PleaseCreateRequest request = new PleaseCreateRequest(emptyTitle, "제발요");
			Member tebah = memberRepository.save(MemberFixture.getTebah());

			assertThatThrownBy(() -> pleaseService.createPlease(tebah, request))
				.isInstanceOf(PleaseException.class);
		}

		@DisplayName("해주세요의 설명이 비어있으면 해주세요 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreatePleaseWithoutDescription(String emptyDescription) {
			PleaseCreateRequest request = new PleaseCreateRequest("피자 사주세요", emptyDescription);
			Member tebah = memberRepository.save(MemberFixture.getTebah());

			assertThatThrownBy(() -> pleaseService.createPlease(tebah, request))
				.isInstanceOf(PleaseException.class);
		}
	}

	@DisplayName("해주세요 삭제 테스트")
	@Nested
	class PleaseDeleteTest {

		@DisplayName("해주세요를 성공적으로 삭제한다.")
		@Test
		void success() {
			Member member = memberRepository.save(MemberFixture.getTebah());
			Please please = pleaseRepository.save(PleaseFixture.getPlease());

			pleaseService.deletePlease(member, please.getId());

			List<Please> pleases = pleaseRepository.findAll();
			assertThat(pleases).hasSize(0);
		}

		@DisplayName("해주세요 작성자가 아니라면 삭제에 실패한다.")
		@Test
		void failToDeleteWithNonAuthor() {
			Member tebah = memberRepository.save(MemberFixture.getTebah());
			Please please = pleaseRepository.save(PleaseFixture.getPlease(tebah.getId()));
			Member anna = memberRepository.save(MemberFixture.getAnna());

			assertThatThrownBy(() -> pleaseService.deletePlease(anna, please.getId()))
				.isInstanceOf(PleaseException.class);
		}

		@DisplayName("해주세요가 존재하지 않으면 삭제에 실패한다.")
		@Test
		void failToDeleteWithNotExistPlease() {
			Member tebah = memberRepository.save(MemberFixture.getTebah());

			assertThatThrownBy(() -> pleaseService.deletePlease(tebah, 0L))
				.isInstanceOf(PleaseException.class);
		}

		@DisplayName("관심있어요 상태 변경시 변경한 해주세요만 관심여부가 변경된다.")
		@Test
		void findAllPlease() {
			Member tebah = memberRepository.save(MemberFixture.getTebah());
			PleaseCreateRequest pleaseCreateRequest = new PleaseCreateRequest("치킨 사주세요", "제발요");
			PleaseCreateRequest pleaseCreateRequest2 = new PleaseCreateRequest("치킨 사줄까요", "제발요요");
			Please please = pleaseService.createPlease(tebah, pleaseCreateRequest);
			pleaseService.createPlease(tebah, pleaseCreateRequest2);

			InterestUpdateRequest interestUpdateRequest = new InterestUpdateRequest(please.getId(), true);
			interestService.updateInterest(tebah, interestUpdateRequest);

			PleaseFindAllResponses pleaseFindAllResponses = pleaseService.findAllPlease(tebah);
			assertThat(pleaseFindAllResponses.pleases().get(0).isInterested()).isTrue();
			assertThat(pleaseFindAllResponses.pleases().get(1).isInterested()).isFalse();
		}
	}
}
