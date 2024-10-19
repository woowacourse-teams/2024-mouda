package mouda.backend.auth.presentation.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.aop.logging.ExceptRequestLogging;
import mouda.backend.auth.business.KakaoAuthService;
import mouda.backend.auth.presentation.controller.swagger.TestAuthSwagger;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.response.RestResponse;

@Profile(value = {"local", "dev"})
@RestController
@RequiredArgsConstructor
public class TestAuthController implements TestAuthSwagger {

	private final KakaoAuthService kakaoAuthService;

	@Override
	@PostMapping("/login/anna")
	@ExceptRequestLogging
	@Profile(value = {"local", "dev"})
	public ResponseEntity<RestResponse<LoginResponse>> loginBasicOauthAnna() {
		LoginResponse response = kakaoAuthService.basicLoginAnna();

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@Override
	@PostMapping("/login/hogee")
	@ExceptRequestLogging
	@Profile(value = {"local", "dev"})
	public ResponseEntity<RestResponse<LoginResponse>> loginBasicOauthHogee() {
		LoginResponse response = kakaoAuthService.basicLoginHogee();

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}
}
