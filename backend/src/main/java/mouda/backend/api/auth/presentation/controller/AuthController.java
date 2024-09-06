package mouda.backend.api.auth.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.api.auth.business.AuthService;
import mouda.backend.api.auth.presentation.controller.swagger.AuthSwagger;
import mouda.backend.core.dto.auth.request.OauthRequest;
import mouda.backend.core.dto.auth.response.LoginResponse;
import mouda.backend.api.common.response.RestResponse;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {

	private final AuthService authService;

	@Override
	@PostMapping("/kakao/oauth")
	public ResponseEntity<RestResponse<LoginResponse>> loginKakaoOauth(@RequestBody OauthRequest oauthRequest) {
		LoginResponse response = authService.oauthLogin(oauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}
}
