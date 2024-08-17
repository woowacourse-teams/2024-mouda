package mouda.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.auth.dto.request.OauthRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.common.RestResponse;

public interface AuthSwagger {

	@Operation(summary = "로그인", description = "카카오 Oauth Code를 사용하여 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginKakaoOauth(@RequestBody OauthRequest oauthRequest);
}
