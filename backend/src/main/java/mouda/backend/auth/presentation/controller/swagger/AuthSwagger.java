package mouda.backend.auth.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.request.GoogleOauthRequest;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.KakaoLoginResponse;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.member.domain.Member;

public interface AuthSwagger {

	@Operation(summary = "카카오 로그인", description = "카카오 Oauth Code를 사용하여 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<KakaoLoginResponse>> loginKakaoOauth(@RequestBody OauthRequest oauthRequest);

	@Operation(summary = "테스트 용 로그인(안나)", description = "테스트 용 가짜 사용자로 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginBasicOauthAnna();

	@Operation(summary = "테스트 용 로그인(호기)", description = "테스트 용 가짜 사용자로 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginBasicOauthHogee();

	@Operation(summary = "애플 로그인", description = "애플 Oauth Code를 사용하여 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginAppleOauth(@RequestBody AppleOauthRequest oauthRequest);

	@Operation(summary = "구글 oauth 로그인", description = "구글 Oauth Code 를 사용하여 로그인한다(accessToken 발급).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "로그인 성공!"),
	})
	ResponseEntity<RestResponse<LoginResponse>> loginGoogleOauth(@RequestBody GoogleOauthRequest googleOauthRequest);

	@Operation(summary = "회원 탈퇴", description = "로그인한 회원을 서비스에서 탈퇴 처리한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "회원 탈퇴 성공!"),
	})
	ResponseEntity<Void> withdraw(@LoginMember Member member);

}
