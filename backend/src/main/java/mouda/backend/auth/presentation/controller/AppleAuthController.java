package mouda.backend.auth.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import mouda.backend.auth.business.AppleAuthService;

@RestController
public class AppleAuthController {

	private AppleAuthService appleAuthService;

	@PostMapping("/v1/oauth/apple")
	public void test(@RequestBody JsonNode jsonNode) {
		String idToken = jsonNode.get("authorization").get("id_token").asText();
		String firstName = jsonNode.get("user").get("name").get("firstName").asText();
		String lastName = jsonNode.get("user").get("name").get("lastName").asText();

		appleAuthService.save(idToken, firstName, lastName);
	}
}
