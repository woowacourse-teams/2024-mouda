package mouda.backend.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.auth.dto.request.LoginRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.common.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthSwagger {

    @Operation(summary = "로그인", description = "닉네임을 사용하여 로그인한다(accessToken 발급).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공!"),
    })
    ResponseEntity<RestResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest);
}
