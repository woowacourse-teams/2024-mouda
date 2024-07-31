package mouda.backend.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mouda.backend.auth.dto.LoginRequest;

public class TokenFixture {

    public static String getTokenWithNicknameTebah() {
        LoginRequest request = new LoginRequest("테바");

        Response response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("v1/auth/login")
            .then().log().all().extract().response();

        return response.jsonPath().getString("data.accessToken");
    }
}
