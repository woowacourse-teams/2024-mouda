package mouda.backend.please.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.fixture.DarakbangSetUp;
import mouda.backend.fixture.PleaseFixture;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.InterestUpdateRequest;
import mouda.backend.please.dto.request.PleaseCreateRequest;
import mouda.backend.please.dto.response.PleaseFindAllResponses;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.PleaseRepository;

@SpringBootTest
class PleaseServiceTest extends DarakbangSetUp {

	@Autowired
	private PleaseRepository pleaseRepository;

	@Autowired
	private PleaseService pleaseService;

	@Autowired
	private InterestService interestService;

	@DisplayName("해주세요 조회 테스트")
	@Nested
	class PleaseFindTest {

		@DisplayName("다락방별 해주세요를 조회한다.")
		@Test
		void success() {
			pleaseRepository.save(PleaseFixture.getPlease(1L, darakbang.getId()));

			assertThat(pleaseService.findAllPlease(darakbang.getId(), darakbangHogee).pleases()).hasSize(1);
			assertThat(pleaseService.findAllPlease(mouda.getId(), moudaHogee).pleases()).hasSize(0);
		}

		@DisplayName("해주세요 목록 조회시 관심이 많은 순서대로 조회하고, 관심이 같다면 생성된 순서대로 조회한다.")
		@Test
		void findAllPlease_isSortedByInterestCount() {
			Please please1 = pleaseRepository.save(Please.builder()
				.authorId(1L)
				.darakbangId(darakbang.getId())
				.title("해주세요1~~")
				.description("해주세요1 해줘~~")
				.build());

			Please please2 = pleaseRepository.save(Please.builder()
				.authorId(1L)
				.darakbangId(darakbang.getId())
				.title("해주세요2~~")
				.description("해주세요2 해줘~~")
				.build());

			Please please3 = pleaseRepository.save(Please.builder()
				.authorId(1L)
				.darakbangId(darakbang.getId())
				.title("해주세요3~~")
				.description("해주세요3 해줘~~")
				.build());

			interestService.updateInterest(darakbang.getId(), darakbangHogee,
				new InterestUpdateRequest(please1.getId(), true));

			interestService.updateInterest(darakbang.getId(), darakbangHogee,
				new InterestUpdateRequest(please2.getId(), true));
			interestService.updateInterest(darakbang.getId(), darakbangAnna,
				new InterestUpdateRequest(please2.getId(), true));

			interestService.updateInterest(darakbang.getId(), darakbangHogee,
				new InterestUpdateRequest(please3.getId(), true));

			PleaseFindAllResponses pleaseFindAllResponses = pleaseService.findAllPlease(
				darakbang.getId(), darakbangHogee);

			assertThat(pleaseFindAllResponses.pleases()).extracting("pleaseId")
				.containsExactly(please2.getId(), please1.getId(), please3.getId());
		}
	}

	@DisplayName("해주세요 생성 테스트")
	@Nested
	class PleaseCreateTest {

		@DisplayName("해주세요를 성공적으로 생성한다.")
		@Test
		void success() {
			PleaseCreateRequest request = new PleaseCreateRequest("치킨 사주세요", "제발요");

			Please please = pleaseService.createPlease(darakbang.getId(), darakbangHogee, request);

			assertThat(please.getId()).isEqualTo(1L);
		}

		@DisplayName("해주세요의 제목이 비어있으면 해주세요 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreatePleaseWithoutTitle(String emptyTitle) {
			PleaseCreateRequest request = new PleaseCreateRequest(emptyTitle, "제발요");

			assertThatThrownBy(() -> pleaseService.createPlease(darakbang.getId(), darakbangHogee, request))
				.isInstanceOf(PleaseException.class);
		}

		@DisplayName("해주세요의 설명이 비어있으면 해주세요 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreatePleaseWithoutDescription(String emptyDescription) {
			PleaseCreateRequest request = new PleaseCreateRequest("피자 사주세요", emptyDescription);

			assertThatThrownBy(() -> pleaseService.createPlease(darakbang.getId(), darakbangHogee, request))
				.isInstanceOf(PleaseException.class);
		}
	}

	@DisplayName("해주세요 삭제 테스트")
	@Nested
	class PleaseDeleteTest {

		@DisplayName("해주세요를 성공적으로 삭제한다.")
		@Test
		void success() {
			Please please = pleaseRepository.save(PleaseFixture.getPlease());

			pleaseService.deletePlease(darakbang.getId(), please.getId(), darakbangHogee);

			List<Please> pleases = pleaseRepository.findAll();
			assertThat(pleases).hasSize(0);
		}

		@DisplayName("해주세요 작성자가 아니라면 삭제에 실패한다.")
		@Test
		void failToDeleteWithNonAuthor() {
			Please please = pleaseRepository.save(PleaseFixture.getPlease(darakbangHogee.getId(), darakbang.getId()));

			assertThatThrownBy(() -> pleaseService.deletePlease(darakbang.getId(), please.getId(), darakbangAnna))
				.isInstanceOf(PleaseException.class);
		}

		@DisplayName("해주세요가 존재하지 않으면 삭제에 실패한다.")
		@Test
		void failToDeleteWithNotExistPlease() {
			assertThatThrownBy(() -> pleaseService.deletePlease(darakbang.getId(), 0L, darakbangHogee))
				.isInstanceOf(PleaseException.class);
		}

		@DisplayName("관심있어요 상태 변경시 변경한 해주세요만 관심여부가 변경된다.")
		@Test
		void findAllPlease() {
			PleaseCreateRequest pleaseCreateRequest = new PleaseCreateRequest("치킨 사주세요", "제발요");
			PleaseCreateRequest pleaseCreateRequest2 = new PleaseCreateRequest("치킨 사줄까요", "제발요요");
			Please please = pleaseService.createPlease(darakbang.getId(), darakbangHogee, pleaseCreateRequest);
			pleaseService.createPlease(darakbang.getId(), darakbangHogee, pleaseCreateRequest2);

			InterestUpdateRequest interestUpdateRequest = new InterestUpdateRequest(please.getId(), true);
			interestService.updateInterest(darakbang.getId(), darakbangHogee, interestUpdateRequest);

			PleaseFindAllResponses pleaseFindAllResponses = pleaseService.findAllPlease(
				darakbang.getId(), darakbangHogee);
			assertThat(pleaseFindAllResponses.pleases()).extracting("isInterested").containsExactly(true, false);
		}
	}
}
