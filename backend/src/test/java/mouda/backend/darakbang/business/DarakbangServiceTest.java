package mouda.backend.darakbang.business;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
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
import mouda.backend.darakbang.implement.InvitationCodeGenerator;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbang.presentation.request.DarakbangCreateRequest;
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

	@DisplayName("다락방 생성 테스트")
	@Nested
	class DarakbangCreateTest {

		@DisplayName("다락방을 성공적으로 생성한다.")
		@Test
		void success() {
			DarakbangCreateRequest request = new DarakbangCreateRequest("우아한테크코스", "테니");
			Member hogee = memberRepository.save(MemberFixture.getHogee());

			Darakbang darakbang = darakbangService.createDarakbang(request, hogee);

			assertThat(darakbang.getId()).isEqualTo(1L);
			assertThat(darakbangMemberRepository.findAll()).hasSize(1);
		}

		@DisplayName("다락방 이름이 중복되면 생성에 실패한다.")
		@Test
		void failToCreateDarakbangWithDuplicatedName() {
			darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			DarakbangCreateRequest request = new DarakbangCreateRequest("우아한테크코스", "테니");

			assertThatThrownBy(() -> darakbangService.createDarakbang(request, hogee))
				.isInstanceOf(DarakbangException.class);
		}

		@DisplayName("다락방 이름이 존재하지 않으면 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreateDarakbangWithoutName(String name) {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			DarakbangCreateRequest request = new DarakbangCreateRequest(name, "테니");

			assertThatThrownBy(() -> darakbangService.createDarakbang(request, hogee))
				.isInstanceOf(DarakbangException.class);
		}

		@DisplayName("닉네임이 존재하지 않으면 다락방 생성에 실패한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void failToCreateDarakbangWithoutNickname(String nickname) {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			DarakbangCreateRequest request = new DarakbangCreateRequest("우아한테크코스", nickname);

			assertThatThrownBy(() -> darakbangService.createDarakbang(request, hogee))
				.isInstanceOf(DarakbangMemberException.class);
		}

		@DisplayName("초대코드가 중복되면 다락방 생성에 실패한다.")
		@Test
		void failToCreateDarakbangWithDuplicatedCode() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			DarakbangCreateRequest request = new DarakbangCreateRequest("우테코", "테니");
			darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());

			darakbangService = new DarakbangService(darakbangRepository, darakbangMemberRepository,
				new InvitationCodeGenerator() {
					@Override
					public String generate() {
						return "SOFABABO1";
					}
				});

			assertThatThrownBy(() -> darakbangService.createDarakbang(request, hogee))
				.isInstanceOf(DarakbangException.class);
		}
	}

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

	@DisplayName("다락방 참여 테스트")
	@Nested
	class DarakbangEnterTest {

		@DisplayName("정상적으로 다락방 가입에 성공한다.")
		@Test
		void success() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			String code = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco()).getCode();
			DarakbangEnterRequest enterRequest = new DarakbangEnterRequest("호기");

			Darakbang darakbang = darakbangService.enter(code, enterRequest, hogee);

			Optional<DarakbangMember> darakbangMember = darakbangMemberRepository.findByDarakbangIdAndMemberId(
				darakbang.getId(), hogee.getId());
			assertThat(darakbangMember).isNotEmpty();
		}

		@DisplayName("다락방에 이미 가입한 멤버라면 가입에 실패한다.")
		@Test
		void failToEnterDarakbangWithDuplicatedNickname() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangManagerWithWooteco(darakbang, hogee));
			DarakbangEnterRequest enterRequest = new DarakbangEnterRequest("호호기기");

			assertThatThrownBy(() -> darakbangService.enter(darakbang.getCode(), enterRequest, hogee))
				.isInstanceOf(DarakbangMemberException.class);
		}

		@DisplayName("다락방에 해당 닉네임이 이미 존재한다면 가입에 실패한다.")
		@Test
		void failToEnterDarakbangWithAlreadyEnteredMember() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangManagerWithWooteco(darakbang, hogee));

			Member anna = memberRepository.save(MemberFixture.getAnna());
			DarakbangEnterRequest enterRequest = new DarakbangEnterRequest("호호기기");

			assertThatThrownBy(() -> darakbangService.enter(darakbang.getCode(), enterRequest, anna))
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
