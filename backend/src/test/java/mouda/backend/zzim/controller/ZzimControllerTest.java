package mouda.backend.zzim.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.service.MoimService;
import mouda.backend.zzim.dto.response.ZzimCheckResponse;
import mouda.backend.zzim.dto.request.ZzimUpdateRequest;
import mouda.backend.zzim.service.ZzimService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZzimControllerTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private MoimService moimService;

	@Autowired
	private ZzimService zzimService;

	@Autowired
	private MemberRepository memberRepository;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Nested
	@DisplayName("회원의 찜 여부 조회 테스트")
	class CheckZzimByMoimAndMemberTest {

		@BeforeEach
		void setUp() {
			databaseCleaner.cleanUp();
		}

		@DisplayName("회원은 해당 모임에 찜을 한 상태이다.")
		@Test
		void checkZzimByMoimAndMember_WhenMemberZzimed() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();

			zzimService.updateZzim(new ZzimUpdateRequest(moim.getId()), tebah);

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/zzim/me")
				.then().statusCode(200)
				.body("data.isZzimed", is(true));
		}

		@DisplayName("회원은 해당 모임에 찜을 하지 않은 상태이다.")
		@Test
		void checkZzimByMoimAndMember_WhenMemberDidntZzim() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/zzim/me")
				.then().statusCode(200)
				.body("data.isZzimed", is(false));
		}
	}

	@Nested
	@DisplayName("찜 상태 변경 테스트")
	class UpdateZzimTest {

		@BeforeEach
		void setUp() {
			databaseCleaner.cleanUp();
		}

		@DisplayName("회원이 모임을 찜한다.")
		@Test
		void zzimMoim() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.body(new ZzimUpdateRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/zzim")
				.then().statusCode(200);

			ZzimCheckResponse zzimCheckResponse = zzimService.checkZzimByMember(moim.getId(), tebah);
			assertThat(zzimCheckResponse.isZzimed()).isTrue();
		}

		@DisplayName("회원이 찜을 취소한다.")
		@Test
		void cancelZzim() {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Moim moim = moimService.createMoim(getMoimCreateRequest(), hogee);

			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member tebah = memberRepository.findByNickname("테바").get();
			zzimService.updateZzim(new ZzimUpdateRequest(moim.getId()), tebah);

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.body(new ZzimUpdateRequest(moim.getId()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().post("/v1/zzim")
				.then().statusCode(200);

			ZzimCheckResponse zzimCheckResponse = zzimService.checkZzimByMember(moim.getId(), tebah);
			assertThat(zzimCheckResponse.isZzimed()).isFalse();
		}
	}

	private MoimCreateRequest getMoimCreateRequest() {
		return new MoimCreateRequest(
			"test", LocalDate.now().plusDays(1), LocalTime.now(),
			"test", 10, "test"
		);
	}
}
