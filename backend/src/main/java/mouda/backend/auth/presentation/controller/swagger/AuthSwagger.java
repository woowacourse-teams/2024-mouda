package mouda.backend.auth.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.response.RestResponse;

public interface AuthSwagger {

	@Operation(summary = "로그인", description = "카카오 Oauth Code를 사용하여 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginKakaoOauth(@RequestBody OauthRequest oauthRequest);

	@Operation(summary = "테스트 용 로그인", description = "테스트 용 가짜 사용자로 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginBasicOauth();
}
