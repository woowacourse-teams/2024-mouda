package mouda.backend.darakbang.business;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbang.presentation.request.DarakbangEnterRequest;
import mouda.backend.darakbang.presentation.response.CodeValidationResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private DarakbangService darakbangService;

	@DisplayName("다락방 초대코드 조회 테스트")
	@Nested
	class DarakbangInvitationCodeReadTest {

		@DisplayName("다락방 초대코드를 성공적으로 조회한다.")
		@Test
		void success() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			DarakbangMember darakbangHogee = darakbangMemberRepository.save(
				DarakbangMemberFixture.getDarakbangManagerWithWooteco(darakbang, hogee));

			assertThatNoException()
				.isThrownBy(() -> darakbangService.findInvitationCode(darakbang.getId(), darakbangHogee));
		}

		@DisplayName("관리자가 아니라면 초대코드 조회에 실패한다.")
		@Test
		void failToReadByNotManager() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			DarakbangMember darakbangHogee = darakbangMemberRepository.save(
				DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));

			assertThatThrownBy(() -> darakbangService.findInvitationCode(darakbang.getId(), darakbangHogee))
				.isInstanceOf(DarakbangMemberException.class);
		}
	}

	@DisplayName("다락방 참여 동시성 테스트")
	@Nested
	class DarakbangEnterConcurrencyTest {

		@DisplayName("한 명의 회원이 다락방에 여러 번 참여 시도하면 실패한다.")
		@Test
		void failToEnterDarakbangTwoTimes() throws InterruptedException {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangManagerWithWooteco(darakbang, hogee));

			int threadCount = 2;
			ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
			CountDownLatch latch = new CountDownLatch(threadCount);

			DarakbangEnterRequest request = new DarakbangEnterRequest("안나");
			Member member = memberRepository.save(MemberFixture.getAnna());
			for (int i = 0; i < threadCount; i++) {
				executorService.execute(() -> {
					try {
						darakbangService.enter(darakbang.getCode(), request, member);
					} finally {
						latch.countDown();
					}
				});
			}
			latch.await();
			executorService.shutdown();

			assertThat(darakbangMemberRepository.findAllByDarakbangId(darakbang.getId())).hasSize(2);
		}
	}

	@DisplayName("다락방 초대코드 검증 테스트")
	@Nested
	class DarakbangCodeValidateTest {

		@DisplayName("다락방 초대코드 유효성 검증에 성공한다.")
		@Test
		void success() {
			memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());

			CodeValidationResponse codeValidationResponse = darakbangService.validateCode(darakbang.getCode());

			assertThat(codeValidationResponse.name()).isNotBlank();
		}

		@DisplayName("다락방 초대코드 유효성 검증에 실패한다.")
		@ValueSource(strings = "INVALID")
		@NullAndEmptySource
		@ParameterizedTest
		void failToValidateCode(String code) {
			memberRepository.save(MemberFixture.getHogee());
			darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());

			assertThatThrownBy(() -> darakbangService.validateCode(code))
				.isInstanceOf(DarakbangException.class);
		}
	}
}
