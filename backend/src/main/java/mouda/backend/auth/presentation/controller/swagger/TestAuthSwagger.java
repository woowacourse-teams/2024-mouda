package mouda.backend.auth.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.response.RestResponse;

public interface TestAuthSwagger {

	@Operation(summary = "테스트 용 로그인(안나)", description = "다락방과 모임을 생성하고 참여한 안나의 토큰 발급. 매번 같은 안나임.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginBasicOauthAnna();

	@Operation(summary = "테스트 용 로그인(호기)", description = "매번 새롭게 회원가입하는 호기의 토큰 발급. 매번 다른 호기임.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginBasicOauthHogee();
}
