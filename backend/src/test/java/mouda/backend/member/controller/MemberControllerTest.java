package mouda.backend.member.controller;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import mouda.backend.fixture.TokenFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
	    RestAssured.port = port;
	}

	@DisplayName("로그인 된 회원의 정보를 조회한다.")
	@Test
	void findMyInfo() {
		String accessToken = TokenFixture.getTokenWithNicknameTebah();

		RestAssured.given()
			.header("Authorization", "Bearer " + accessToken)
			.when().get("/v1/member/mine")
			.then().statusCode(200)
			.body("data.nickname", is("테바"));
	}
}
