package mouda.backend.please.controller;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.PleaseFixture;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.please.domain.Please;
import mouda.backend.please.repository.PleaseRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PleaseControllerTest {

	@Autowired
	PleaseRepository pleaseRepository;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		databaseCleaner.cleanUp();
		RestAssured.port = port;

		Please please = PleaseFixture.getPleaseWithAuthorId1L();
		pleaseRepository.save(please);
	}

	@DisplayName("해주세요 전체 목록을 조회한다.")
	@Test
	void findAllPlease() {
		String accessToken = TokenFixture.getTokenWithNicknameTebah();

		RestAssured.given()
			.log()
			.all()
			.header("Authorization", "Bearer " + accessToken)
			.when()
			.get("/v1/please")
			.then()
			.log()
			.all()
			.statusCode(200)
			.body("data.pleases.size()", is(1));
	}
}

