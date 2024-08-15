package mouda.backend.darakbang.service;

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
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.repository.DarakbangRepository;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.fixture.DarakbangFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;

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

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

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
}
