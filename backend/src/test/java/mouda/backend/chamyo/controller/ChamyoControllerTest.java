package mouda.backend.chamyo.controller;

import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChamyoControllerTest {

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private DatabaseCleaner dbCleaner;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Nested
	@DisplayName("현재 로그인 된 회원의 참여 여부를 조회한다.")
	class ChamyoByMemberTest {

		@AfterEach
		void setUp() {
			dbCleaner.cleanUp();
		}

		@DisplayName("현재 로그인 된 회원은 모임을 개설한 회원이다.")
		@ParameterizedTest
		@MethodSource("provideMoimRoleAndExpectedResult")
		void success(MoimRole role, String expectedResult) {
			Moim moim = Moim.builder()
				.title("test")
				.date(LocalDate.now().plusDays(1))
				.time(LocalTime.now())
				.place("test")
				.maxPeople(10)
				.description("test")
				.build();
			Moim savedMoim = moimRepository.save(moim);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member member = memberRepository.findByNickname("테바").get();

			Chamyo chamyo = Chamyo.builder()
				.member(member)
				.moim(moim)
				.moimRole(role)
				.build();
			chamyoRepository.save(chamyo);

			RestAssured.given().log().all()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", savedMoim.getId())
				.when().get("/v1/chamyo/me")
				.then().log().all().statusCode(200)
				.body("data.role", is(expectedResult));
		}

		private static Stream<Arguments> provideMoimRoleAndExpectedResult() {
			return Stream.of(
				Arguments.of(MoimRole.MOIMER, "MOIMER"),
				Arguments.of(MoimRole.MOIMEE, "MOIMEE"),
				Arguments.of(MoimRole.NON_MOIMEE, "NON_MOIMEE")
			);
		}
	}

	@Nested
	@DisplayName("모임의 모든 참여자 조회 테스트")
	class FindAllChamyoTest {

		@AfterEach
		void setUp() {
			dbCleaner.cleanUp();
		}

		@DisplayName("모든 참여자를 조회한다.")
		@Test
		void success() {
			Moim moim = Moim.builder()
				.title("test")
				.date(LocalDate.now().plusDays(1))
				.time(LocalTime.now())
				.place("test")
				.maxPeople(10)
				.description("test")
				.build();
			Moim savedMoim = moimRepository.save(moim);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member member = memberRepository.findByNickname("테바").get();
			Member member1 = memberRepository.save(Member.builder().nickname("테바1").build());

			Chamyo chamyo = Chamyo.builder()
				.member(member)
				.moim(moim)
				.moimRole(MoimRole.MOIMER)
				.build();
			chamyoRepository.save(chamyo);

			Chamyo chamyo1 = Chamyo.builder()
				.member(member1)
				.moim(moim)
				.moimRole(MoimRole.MOIMEE)
				.build();
			chamyoRepository.save(chamyo1);

			RestAssured.given().log().all()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", savedMoim.getId())
				.when().get("/v1/chamyo/all")
				.then().log().all().statusCode(200)
				.body("data.chamyos.size()", is(2))
				.body("data.chamyos[0].role", is("MOIMER"))
				.body("data.chamyos[0].nickname", is("테바"))
				.body("data.chamyos[1].role", is("MOIMEE"))
				.body("data.chamyos[1].nickname", is("테바1"));
		}
	}
}
