package mouda.backend.auth.presentation.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.business.AppleAuthService;
import mouda.backend.auth.util.TokenDecoder;

@RestController
@Slf4j
public class AppleAuthController {

	private AppleAuthService appleAuthService;

	@PostMapping("/v1/oauth/apple")
	public ResponseEntity<Void> test(
		@RequestParam("id_token") String id_token,
		@RequestParam("user") String user
	) {
		String firstName = user.get("name").get("firstName").asText();
		String lastName = user.get("name").get("lastName").asText();
		Map<String, String> stringStringMap = TokenDecoder.parseIdToken(id_token);
		for (String s : stringStringMap.keySet()) {
			log.info("{} : {}", s, stringStringMap.get(s));
		}
		//
		log.error("firstName : {}, lastNAme: {}", firstName, lastName);
		appleAuthService.save(id_token, firstName, lastName);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "https://dev.mouda.site/oauth/apple");
		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}
}
