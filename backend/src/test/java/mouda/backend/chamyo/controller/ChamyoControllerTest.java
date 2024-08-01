package mouda.backend.chamyo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import mouda.backend.chamyo.dto.MoimChamyoRequest;
import mouda.backend.chamyo.service.ChamyoService;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.moim.service.MoimService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChamyoControllerTest {

	@Autowired
	private ChamyoService chamyoService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private DatabaseCleaner dbCleaner;

	@Autowired
	private MoimService moimService;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Nested
	@DisplayName("현재 로그인 된 회원의 참여 여부를 조회한다.")
	class FindMoimRoleByMemberTest {

		@AfterEach
		void setUp() {
			dbCleaner.cleanUp();
		}

		@DisplayName("현재 로그인 된 회원은 방장이다.")
		@Test
		void findMoimRole_WhemMemberIsMoimer() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequestByMaxPeople(2), tebah);

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/chamyo/me")
				.then().statusCode(200)
				.body("data.role", is("MOIMER"));
		}

		@DisplayName("현재 로그인 된 회원은 참여자이다.")
		@Test
		void findMoimRole_WhemMemberIsMoimee() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequestByMaxPeople(2), hogee);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), tebah);

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/chamyo/me")
				.then().log().all().statusCode(200)
				.body("data.role", is("MOIMEE"));
		}

		@DisplayName("현재 로그인 된 회원은 아직 모임에 참여하지 않은 상태이다.")
		@Test
		void findMoimRole_WhemMemberIsNonMoimee() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequestByMaxPeople(2), hogee);

			RestAssured.given().log().all()
				.header("Authorization", "Bearer " + TokenFixture.getTokenWithNicknameTebah())
				.param("moimId", moim.getId())
				.when().get("/v1/chamyo/me")
				.then().log().all().statusCode(200)
				.body("data.role", is("NON_MOIMEE"));
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
			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member member = memberRepository.findByNickname("테바").get();
			Member member1 = memberRepository.save(MemberFixture.getHogee());

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), member);
			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), member1);

			RestAssured.given().log().all()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/chamyo/all")
				.then().log().all().statusCode(200)
				.body("data.chamyos.size()", is(2))
				.body("data.chamyos[0].role", is("MOIMEE"))
				.body("data.chamyos[1].role", is("MOIMEE"));
		}
	}

	@Nested
	@DisplayName("모임 참여 테스트")
	class ChamyoMoimTest {

		@AfterEach
		void tearDown() {
			dbCleaner.cleanUp();
		}

		@DisplayName("모임에 성공적으로 참여한다")
		@Test
		void success() {
			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());
			String accessToken = TokenFixture.getTokenWithNicknameTebah();

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.body(new MoimChamyoRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/chamyo")
				.then().statusCode(200);
		}

		@DisplayName("모임에 이미 참여한 경우 예외가 발생한다.")
		@Test
		void fail_whenAlreadyChamyo() {
			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member member = memberRepository.findByNickname("테바").get();

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), member);

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.body(new MoimChamyoRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/chamyo")
				.then().statusCode(400);
		}

		@DisplayName("모임의 최대 인원을 초과하여 참여할 경우 예외가 발생한다.")
		@Test
		void fail_whenMoimFull() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequestByMaxPeople(1), hogee);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.body(new MoimChamyoRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/chamyo")
				.then().statusCode(400);
		}

		@DisplayName("최대 인원을 초과하지 않아도 모임장이 모집을 종료한 경우 예외가 발생한다.")
		@Test
		void fail_whenMoimClosed() {
			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());
			moimService.updateMoimStatusById(moim.getId(), MoimStatus.COMPLETED);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();

			RestAssured.given().log().all()
				.header("Authorization", "Bearer " + accessToken)
				.body(new MoimChamyoRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/chamyo")
				.then().log().all().statusCode(400);
		}

		@DisplayName("취소된 모임은 참여할 수 없다.")
		@Test
		void fail_whenMoimCancled() {
			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());
			moimService.updateMoimStatusById(moim.getId(), MoimStatus.CANCELED);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();

			RestAssured.given().log().all()
				.header("Authorization", "Bearer " + accessToken)
				.body(new MoimChamyoRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/chamyo")
				.then().log().all().statusCode(400);
		}

		@DisplayName("마지막 참여로 인해 모임이 꽉 찬 경우 모임 상태를 변경한다.")
		@Test
		void checkIfMoimStatusChanged() {
			Member tebah = memberRepository.save(MemberFixture.getTebah());
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequestByMaxPeople(2), tebah);

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), hogee);

			Moim updatedMoim = moimRepository.findById(moim.getId()).get();
			assertThat(updatedMoim.getMoimStatus()).isEqualTo(MoimStatus.COMPLETED);
		}
	}

	private MoimCreateRequest getMoimCreateRequestByMaxPeople(int maxPeople) {
		return new MoimCreateRequest(
			"test", LocalDate.now().plusDays(1), LocalTime.now(),
			"test", maxPeople, "test"
		);
	}
}
