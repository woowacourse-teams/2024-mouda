package mouda.backend.auth.presentation.controller;

import org.springframework.beans.factory.annotation.Value;
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
import mouda.backend.auth.presentation.request.GoogleLoginRequest;
import mouda.backend.auth.presentation.request.KakaoConvertRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {
	
	@Value("${oauth.apple.redirection}")
	private String redirection;

	private final KakaoAuthService kakaoAuthService;
	private final GoogleAuthService googleAuthService;
	private final AppleAuthService appleAuthService;

	@PostMapping("/kakao")
	public ResponseEntity<Void> convert(
		@LoginMember Member member,
		@RequestBody KakaoConvertRequest kakaoConvertRequest
	) {
		kakaoAuthService.convert(member, kakaoConvertRequest);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/google")
	public ResponseEntity<RestResponse<LoginResponse>> loginGoogle(
		@RequestBody GoogleLoginRequest googleLoginRequest
	) {
		LoginResponse response = googleAuthService.login(googleLoginRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@PostMapping("/apple")
	public ResponseEntity<Void> loginApple(
		@RequestParam("id_token") String idToken,
		@RequestParam(name = "user", required = false) String user
	) {
		String accessToken = appleAuthService.login(idToken, user);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", redirection + accessToken);
		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}
}
