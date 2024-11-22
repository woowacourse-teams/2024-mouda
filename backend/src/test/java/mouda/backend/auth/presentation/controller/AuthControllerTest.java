package mouda.backend.auth.presentation.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import mouda.backend.auth.business.AppleAuthService;
import mouda.backend.auth.presentation.response.LoginResponse;

@SpringBootTest
class AuthControllerTest {

	@Autowired
	private AuthController authController;

	@Value("${oauth.apple.redirection}")
	private String redirection;

	@MockBean
	private AppleAuthService appleAuthService;

	@DisplayName("애플 로그인 후 토큰과 전환 여부를 반환한다.")
	@Test
	void loginApple() {
		when(appleAuthService.login(anyString(), anyString())).thenReturn(
			new LoginResponse("token", true)
		);

		ResponseEntity<Void> responseEntity = authController.loginApple("idtoken", "user");
		HttpHeaders headers = responseEntity.getHeaders();
		assertThat(headers.containsKey("Location")).isTrue();
		List<String> location = headers.get("Location");
		assertThat(location).hasSize(1);
		String format = String.format(redirection, "token", "true");
		assertThat(location.get(0)).isEqualTo(format);
	}
}
