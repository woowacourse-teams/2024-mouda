package mouda.backend.auth.presentation.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.business.AppleAuthService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AppleAuthController {

	private final AppleAuthService appleAuthService;

	@PostMapping("/v1/oauth/apple")
	public ResponseEntity<Void> test(
		@RequestParam("id_token") String id_token,
		@RequestParam(name = "user", required = false) String user
	) throws IOException {
		if (user != null) {
			appleAuthService.save(id_token, user);
		}
		String accessToken = appleAuthService.getAccessToken(id_token);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "https://dev.mouda.site/oauth/apple?token=" + accessToken);
		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}
}