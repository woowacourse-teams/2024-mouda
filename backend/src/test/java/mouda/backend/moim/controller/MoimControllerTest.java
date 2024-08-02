package mouda.backend.moim.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mouda.backend.config.ChamyoCreator;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.TokenFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MoimControllerTest {

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
}
