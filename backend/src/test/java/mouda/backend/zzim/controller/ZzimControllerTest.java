package mouda.backend.zzim.controller;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.zzim.domain.Zzim;
import mouda.backend.zzim.repository.ZzimRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZzimControllerTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ZzimRepository zzimRepository;

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

		@DisplayName("회원이 해당 모임에 찜을 한 경우 true를 반환한다.")
		@Test
		void checkZzimByMoimAndMember_WhenMemberZzimed() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Member member = memberRepository.findByNickname("테바").get();

			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());

			Zzim zzim = Zzim.builder().moim(moim).member(member).build();
			zzimRepository.save(zzim);

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/zzim/me")
				.then().statusCode(200)
				.body("data.isZzimed", is(true));
		}

		@DisplayName("회원이 해당 모임에 찜을 한 경우 false를 반환한다.")
		@Test
		void checkZzimByMoimAndMember_WhenMemberDidntZzim() {
			String accessToken = TokenFixture.getTokenWithNicknameTebah();
			Moim moim = moimRepository.save(MoimFixture.getSoccerMoim());

			RestAssured.given()
				.header("Authorization", "Bearer " + accessToken)
				.param("moimId", moim.getId())
				.when().get("/v1/zzim/me")
				.then().statusCode(200)
				.body("data.isZzimed", is(false));
		}
	}
}
