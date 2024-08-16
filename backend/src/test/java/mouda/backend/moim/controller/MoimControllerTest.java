package mouda.backend.moim.controller;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mouda.backend.IgnoreNotificationTest;
import mouda.backend.chamyo.dto.request.MoimChamyoRequest;
import mouda.backend.chamyo.service.ChamyoService;
import mouda.backend.config.ChamyoCreator;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimEditRequest;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.moim.service.MoimService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MoimControllerTest extends IgnoreNotificationTest {

	@Autowired
	private ChamyoService chamyoService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MoimService moimService;

	@Autowired
	ChamyoCreator chamyoCreator;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		databaseCleaner.cleanUp();
		RestAssured.port = port;
		chamyoCreator.setUpTwoPastThreeUpcomingMoim();
	}

	@Nested
	@DisplayName("모집 완료 테스트")
	class CompleteMoimTest {

		@AfterEach
		void tearDown() {
			databaseCleaner.cleanUp();
		}

		@DisplayName("방장이 모집을 완료한다.")
		@Test
		void success() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/complete", moim.getId())
				.then()
				.statusCode(200);

			assertThat(moimRepository.findById(moim.getId()).get().getMoimStatus()).isEqualTo(MoimStatus.COMPLETED);
		}

		@DisplayName("방장이 아닌 참여자는 모집을 완료할 수 없다.")
		@Test
		void fail_whenMemberIsNotMoimer() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/complete", moim.getId())
				.then()
				.log()
				.all()
				.statusCode(403);
		}

		@DisplayName("이미 모집이 완료된 경우 모집을 완료할 수 없다.")
		@Test
		void fail_whenMoimIsAlreadyCompleted() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.completeMoim(moim.getId(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/complete", moim.getId())
				.then()
				.statusCode(400);
		}

		@DisplayName("취소된 모임에 대해서는 모집을 완료할 수 없다.")
		@Test
		void fail_whenMoimIsAlreadyCanceled() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.cancelMoim(moim.getId(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/complete", moim.getId())
				.then()
				.statusCode(400);
		}
	}

	@Nested
	@DisplayName("모임 취소 테스트")
	class CancelMoimTest {

		@BeforeEach
		void setUp() {
			databaseCleaner.cleanUp();
		}

		@DisplayName("방장이 모임을 취소한다.")
		@Test
		void success() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/cancel", moim.getId())
				.then()
				.statusCode(200);

			assertThat(moimRepository.findById(moim.getId()).get().getMoimStatus()).isEqualTo(MoimStatus.CANCELED);
		}

		@DisplayName("방장이 아닌 참여자는 모임을 취소할 수 없다.")
		@Test
		void fail_whenMemberIsNotMoimer() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/cancel", moim.getId())
				.then()
				.log()
				.all()
				.statusCode(403);
		}

		@DisplayName("이미 취소된 모임은 취소할 수 없다.")
		@Test
		void fail_whenMoimIsAlreadyCanceled() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.cancelMoim(moim.getId(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/cancel", moim.getId())
				.then()
				.statusCode(400);
		}
	}

	@Nested
	@DisplayName("모임 재개 테스트")
	class ReopenMoimTest {

		@BeforeEach
		void setUp() {
			databaseCleaner.cleanUp();
		}

		@DisplayName("방장이 모임을 재개한다.")
		@Test
		void success() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.completeMoim(moim.getId(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/reopen", moim.getId())
				.then()
				.statusCode(200);

			assertThat(moimRepository.findById(moim.getId()).get().getMoimStatus()).isEqualTo(MoimStatus.MOIMING);
		}

		@DisplayName("방장이 아닌 참여자는 모임을 재개할 수 없다.")
		@Test
		void fail_whenMemberIsNotMoimer() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/reopen", moim.getId())
				.then()
				.log()
				.all()
				.statusCode(403);
		}

		@DisplayName("인원이 가득 찬 모임은 재개할 수 없다.")
		@Test
		void fail_whenMoimIsFull() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(2), tebah);
			Member hogee = memberRepository.save(MemberFixture.getHogee());

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), hogee);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/reopen", moim.getId())
				.then()
				.statusCode(400);
		}

		@DisplayName("이미 모집 중인 모임은 재개할 수 없다.")
		@Test
		void fail_whenAlreadyMoiming() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/reopen", moim.getId())
				.then()
				.statusCode(400);
		}

		@DisplayName("이미 취소된 모임은 재개할 수 없다.")
		@Test
		void fail_whenMoimIsAlreadyCanceled() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.cancelMoim(moim.getId(), tebah);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.patch("/v1/moim/{moimId}/reopen", moim.getId())
				.then()
				.statusCode(400);
		}
	}

	@Nested
	@DisplayName("모임 수정 테스트")
	class EditMoimTest {

		@AfterEach
		void tearDown() {
			databaseCleaner.cleanUp();
		}

		@DisplayName("방장이 아닌 참여자는 모임을 수정할 수 없다.")
		@Test
		void fail_whenMemberIsNotMoimer() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			chamyoService.chamyoMoim(new MoimChamyoRequest(moim.getId()), memberRepository.findByNickname("테바").get());
			MoimEditRequest request = getMoimEditRequest(moim.getId(), "test", LocalDate.now().plusDays(1),
				LocalTime.now(), "test", 10, "test");

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.body(request)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.patch("/v1/moim")
				.then()
				.log()
				.all()
				.statusCode(403);
		}

		@DisplayName("완료된 모임은 수정할 수 없다.")
		@Test
		void fail_whenMoimCompleted() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.completeMoim(moim.getId(), tebah);
			MoimEditRequest request = getMoimEditRequest(moim.getId(), "test", LocalDate.now().plusDays(1),
				LocalTime.now(), "test", 10, "test");

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.body(request)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.patch("/v1/moim")
				.then()
				.log()
				.all()
				.statusCode(400);
		}

		@DisplayName("취소된 모임은 수정할 수 없다.")
		@Test
		void fail_whenMoimCanceled() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			moimService.cancelMoim(moim.getId(), tebah);
			MoimEditRequest request = getMoimEditRequest(moim.getId(), "test", LocalDate.now().plusDays(1),
				LocalTime.now(), "test", 10, "test");

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.body(request)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.patch("/v1/moim")
				.then()
				.log()
				.all()
				.statusCode(400);
		}

		@DisplayName("모임을 수정한다.")
		@Test
		void success() {
			String newTitle = "newTitle";
			LocalDate newDate = LocalDate.now().plusDays(7);
			LocalTime newTime = LocalTime.now().minusHours(1);
			String newPlace = "newPlace";
			int newMaxPeople = 20;
			String newDescription = "newDescription";

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			Moim moim = moimService.createMoim(getMoimCreateRequest(), tebah);

			MoimEditRequest request = getMoimEditRequest(moim.getId(), newTitle, newDate, newTime, newPlace,
				newMaxPeople, newDescription);

			RestAssured.given()
				.log()
				.all()
				.header("Authorization", "Bearer " + accessToken)
				.body(request)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.patch("/v1/moim")
				.then()
				.log()
				.all()
				.statusCode(200);

			Moim newMoim = moimRepository.findById(moim.getId()).get();
			assertThat(newMoim.getTitle()).isEqualTo(newTitle);
			assertThat(newMoim.getDate()).isEqualTo(newDate);
			assertThat(newMoim.getTime().getHour()).isEqualTo(newTime.getHour());
			assertThat(newMoim.getTime().getMinute()).isEqualTo(newTime.getMinute());
			assertThat(newMoim.getTime().getSecond()).isEqualTo(newTime.getSecond());
			assertThat(newMoim.getPlace()).isEqualTo(newPlace);
			assertThat(newMoim.getMaxPeople()).isEqualTo(newMaxPeople);
			assertThat(newMoim.getDescription()).isEqualTo(newDescription);
		}
	}

	private MoimCreateRequest getMoimCreateRequest() {
		return getMoimCreateRequest(10);
	}

	private MoimCreateRequest getMoimCreateRequest(int maxPeople) {
		return new MoimCreateRequest("test", LocalDate.now().plusDays(1), LocalTime.now(), "test", maxPeople, "test");
	}

	private MoimEditRequest getMoimEditRequest(Long moimId, String title, LocalDate date, LocalTime time, String place,
		int maxPeople, String description) {
		return new MoimEditRequest(moimId, title, date, time, place, maxPeople, description);
	}

	@DisplayName("내가 참여한 모든 모임을 조회한다.")
	@Test
	void findAllMyMoim() {
		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + TokenFixture.getTokenWithNicknameTebah())
			.when().get("v1/moim/mine")
			.then().log().all()
			.statusCode(200)
			.body("data.moims.size()", is(5));
	}

	@DisplayName("내가 참여한 모든 모임 중 지난 모임을 조회한다.")
	@Test
	void findAllMyPastMoim() {
		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + TokenFixture.getTokenWithNicknameTebah())
			.when().get("v1/moim/mine?filter=past")
			.then().log().all()
			.statusCode(200)
			.body("data.moims.size()", is(2));
	}

	@DisplayName("내가 참여한 모든 모임 중 다가오는 조회한다.")
	@Test
	void findAllMyUpcomingMoim() {
		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + TokenFixture.getTokenWithNicknameTebah())
			.when().get("v1/moim/mine?filter=upcoming")
			.then().log().all()
			.statusCode(200)
			.body("data.moims.size()", is(3));
	}

	@DisplayName("찜한 모임을 조회한다.")
	@Test
	void findAllZzimMoim() {
		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + TokenFixture.getTokenWithNicknameTebah())
			.when().get("v1/moim/zzim")
			.then().log().all()
			.statusCode(200)
			.body("data.moims.size()", is(3));
	}
}
