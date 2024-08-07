package mouda.backend.please.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.PleaseFixture;
import mouda.backend.fixture.TokenFixture;
import mouda.backend.please.domain.Interest;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.InterestUpdateRequest;
import mouda.backend.please.repository.InterestRepository;
import mouda.backend.please.repository.PleaseRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterestControllerTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private PleaseRepository pleaseRepository;

	@Autowired
	private InterestRepository interestRepository;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		databaseCleaner.cleanUp();
		RestAssured.port = port;

		Please please = PleaseFixture.getPleaseWithAuthorId1L();
		pleaseRepository.save(please);
	}

	@DisplayName("관심있어요 상태를 변경한다. -> 관심있어요.")
	@Test
	void updateInterest_toInterested() {
		String accessToken = TokenFixture.getTokenWithNicknameTebah();
		InterestUpdateRequest request = new InterestUpdateRequest(1L, true);

		RestAssured.given()
			.log()
			.all()
			.header("Authorization", "Bearer " + accessToken)
			.body(request)
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/interest")
			.then()
			.log()
			.all()
			.statusCode(200);

		Optional<Interest> byMemberIdAndPleaseId = interestRepository.findByMemberIdAndPleaseId(1L, 1L);
		assertThat(byMemberIdAndPleaseId.isPresent()).isTrue();
	}

	@DisplayName("관심있어요 상태를 변경한다. -> 관심있어요 취소.")
	@Test
	void updateInterest_toNotInterested() {
		String accessToken = TokenFixture.getTokenWithNicknameTebah();
		InterestUpdateRequest request = new InterestUpdateRequest(1L, false);

		RestAssured.given()
			.log()
			.all()
			.header("Authorization", "Bearer " + accessToken)
			.body(request)
			.contentType(ContentType.JSON)
			.when()
			.post("/v1/interest")
			.then()
			.log()
			.all()
			.statusCode(200);

		Optional<Interest> byMemberIdAndPleaseId = interestRepository.findByMemberIdAndPleaseId(1L, 1L);
		assertThat(byMemberIdAndPleaseId.isPresent()).isFalse();
	}
}
