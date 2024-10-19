package mouda.backend.auth.presentation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.AppleAuthService;
import mouda.backend.auth.business.GoogleAuthService;
import mouda.backend.auth.business.KakaoAuthService;
import mouda.backend.auth.presentation.controller.swagger.AuthSwagger;
import mouda.backend.auth.presentation.request.GoogleOauthRequest;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.response.RestResponse;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {

	private final KakaoAuthService kakaoAuthService;
	private final GoogleAuthService googleAuthService;
	private final AppleAuthService appleAuthService;

	@PostMapping("/kakao")
	public ResponseEntity<RestResponse<LoginResponse>> loginKakaoOauth(@RequestBody OauthRequest oauthRequest) {
		LoginResponse response = kakaoAuthService.oauthLogin(oauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@PostMapping("/google")
	public ResponseEntity<RestResponse<LoginResponse>> loginGoogleOauth(
		@RequestBody GoogleOauthRequest googleOauthRequest) {
		LoginResponse response = googleAuthService.oauthLogin(googleOauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@PostMapping("/apple")
	public ResponseEntity<Void> loginAppleOauth(
		@RequestParam("id_token") String id_token,
		@RequestParam(name = "user", required = false) String user
	) {
		// TODO: 이전에 가입한 적 있지만 DB를 갈아엎어서 user가 들어오지 않는 경우 save에 실패한다.
		if (user != null) {
			appleAuthService.save(id_token, user);
		}
		String accessToken = appleAuthService.login(id_token);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "https://dev.mouda.site/oauth/apple?token=" + accessToken);
		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}
}
