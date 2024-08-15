package mouda.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.dto.request.LoginRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.auth.service.AuthService;
import mouda.backend.common.RestResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {

	private final AuthService authService;
	private final MemberRepository memberRepository;

	@Override
	@PostMapping("/login")
	public ResponseEntity<RestResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
		LoginResponse response = authService.login(loginRequest);

		return ResponseEntity.ok().body(new RestResponse<>(response));
	}

	@GetMapping("/kakao/oauth")
	public String kakao(
		@RequestParam("code") String code,
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "error_description", required = false) String error_description,
		@RequestParam(value = "state", required = false) String state
	) {
		System.out.println(code);
		Member member = new Member(code);
		memberRepository.save(member);
		return code;
	}
}
