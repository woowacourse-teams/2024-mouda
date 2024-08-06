package mouda.backend.please.service;

import static org.assertj.core.api.Assertions.*;

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
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.PleaseCreateRequest;

@SpringBootTest
class PleaseServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PleaseService pleaseService;

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
				.isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("해주세요의 설명이 비어있으면 해주세요 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreatePleaseWithoutDescription(String emptyDescription) {
			PleaseCreateRequest request = new PleaseCreateRequest("피자 사주세요", emptyDescription);
			Member tebah = memberRepository.save(MemberFixture.getTebah());

			assertThatThrownBy(() -> pleaseService.createPlease(tebah, request))
				.isInstanceOf(IllegalArgumentException.class);
		}
	}
}
