package mouda.backend.auth.presentation.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.business.AppleAuthService;
import mouda.backend.auth.util.TokenDecoder;

@RestController
@Slf4j
public class AppleAuthController {

	private AppleAuthService appleAuthService;

	@PostMapping("/v1/oauth/apple")
	public void test(
		@RequestParam("id_token") String id_token,
		@RequestParam("user") JsonNode user
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
	}
}
