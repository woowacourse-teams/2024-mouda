package mouda.backend.config;

import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JacksonConfigTest {

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@AfterEach
	void tearDown() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("날짜 형식에 따른 직렬화 및 역직렬화 테스트")
	@Nested
	class DateFormatTest {

		@DisplayName("yyyy-MM-dd 형식의 날짜를 역직렬화한다.")
		@Test
		void deserialize() {
			Map<String, Object> params = Map.of(
				"title", "title",
				"date", "2024-07-19",
				"time", "12:30",
				"place", "place",
				"maxPeople", 10,
				"authorNickname", "안나",
				"description", "설명"
			);

			RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(params)
				.when().post("/v1/moim")
				.then().statusCode(is(HttpStatus.OK.value()));
		}

		@DisplayName("yyyy-MM-dd 형식이 아닌 날짜가 입력되면 예외가 발생한다.")
		@Test
		void deserialize_when_invalidDateFormat() {
			Map<String, Object> params = Map.of(
				"title", "title",
				"date", "2024/07/19",
				"time", "12:30",
				"place", "place",
				"maxPeople", 10,
				"authorNickname", "안나",
				"description", "설명"
			);

			RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(params)
				.when().post("/v1/moim")
				.then().statusCode(is(HttpStatus.BAD_REQUEST.value()));
		}

		@DisplayName("날짜는 yyyy-MM-dd 형식으로 직렬화된다.")
		@Test
		void serialize() {
			Moim moim = Moim.builder()
				.title("title")
				.date(LocalDate.parse("2024-07-19"))
				.time(LocalTime.parse("12:30"))
				.place("place")
				.maxPeople(10)
				.description("설명")
				.build();

			Moim saved = moimRepository.save(moim);

			RestAssured.given()
				.when().get("/v1/moim/" + saved.getId())
				.then().statusCode(is(HttpStatus.OK.value()))
				.body("data.date", is("2024-07-19"));
		}
	}

	@DisplayName("시간 형식에 따른 직렬화 및 역직렬화 테스트")
	@Nested
	class TimeFormatTest {

		@DisplayName("HH:mm 형식의 시간을 역직렬화한다.")
		@Test
		void deserialize() {
			Map<String, Object> params = Map.of(
				"title", "title",
				"date", "2024-07-19",
				"time", "12:30",
				"place", "place",
				"maxPeople", 10,
				"authorNickname", "안나",
				"description", "설명"
			);

			RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(params)
				.when().post("/v1/moim")
				.then().statusCode(is(HttpStatus.OK.value()));
		}

		@DisplayName("HH:mm 형식이 아닌 시간이 입력되면 예외가 발생한다.")
		@Test
		void deserialize_when_invalidTimeFormat() {
			Map<String, Object> params = Map.of(
				"title", "title",
				"date", "2024-07-19",
				"time", "12-30",
				"place", "place",
				"maxPeople", 10,
				"authorNickname", "안나",
				"description", "설명"
			);

			RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(params)
				.when().post("/v1/moim")
				.then().statusCode(is(HttpStatus.BAD_REQUEST.value()));
		}

		@DisplayName("시간은 HH:mm 형식으로 직렬화된다.")
		@Test
		void serialize() {
			Moim moim = Moim.builder()
				.title("title")
				.date(LocalDate.now().plusDays(1))
				.time(LocalTime.parse("12:30"))
				.place("place")
				.maxPeople(10)
				.description("설명")
				.build();
			Moim saved = moimRepository.save(moim);

			RestAssured.given()
				.when().get("/v1/moim/" + saved.getId())
				.then().statusCode(is(HttpStatus.OK.value()))
				.body("data.time", is("12:30"));
		}
	}
}
