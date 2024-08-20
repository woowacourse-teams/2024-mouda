package mouda.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.dto.request.OauthRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.auth.service.AuthService;
import mouda.backend.common.RestResponse;

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

	@PostMapping("/login")
	public ResponseEntity<RestResponse<LoginResponse>> loginKakaoOauth(@RequestParam Long oauthRequest) {
		LoginResponse response = authService.processLogin(oauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}
}
