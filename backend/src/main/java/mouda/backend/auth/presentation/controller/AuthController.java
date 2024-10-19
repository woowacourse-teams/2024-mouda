package mouda.backend.auth.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.AppleAuthService;
import mouda.backend.auth.business.CommonAuthService;
import mouda.backend.auth.business.GoogleAuthService;
import mouda.backend.auth.business.KakaoAuthService;
import mouda.backend.auth.presentation.controller.swagger.AuthSwagger;
import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.request.GoogleOauthRequest;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {

	private final KakaoAuthService kakaoAuthService;
	private final AppleAuthService appleAuthService;
	private final GoogleAuthService googleAuthService;
	private final CommonAuthService commonAuthService;

	@Override
	@PostMapping("/kakao")
	public ResponseEntity<RestResponse<LoginResponse>> loginKakaoOauth(@RequestBody OauthRequest oauthRequest) {
		LoginResponse response = kakaoAuthService.oauthLogin(oauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@Override
	@PostMapping("/google")
	public ResponseEntity<RestResponse<LoginResponse>> loginGoogleOauth(
		@RequestBody GoogleOauthRequest googleOauthRequest) {
		LoginResponse response = googleAuthService.oauthLogin(googleOauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@Override
	@PostMapping("/apple/oauth")
	public ResponseEntity<RestResponse<LoginResponse>> loginAppleOauth(@RequestBody AppleOauthRequest oauthRequest) {
		LoginResponse response = appleAuthService.oauthLogin(oauthRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Void> withdraw(@LoginMember Member member) {
		commonAuthService.withdraw(member);

		return ResponseEntity.ok().build();
	}
}
