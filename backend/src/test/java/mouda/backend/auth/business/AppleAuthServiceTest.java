package mouda.backend.auth.business;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;

@SpringBootTest
class AppleAuthServiceTest {

	@Autowired
	private AppleAuthService appleAuthService;

	@DisplayName("애플 로그인 요청을 보내면 access token을 반환한다.")
	@Test
	@Disabled("실제 Resource Server에게 요청을 보내는 테스트이다. 프론트 서버를 켜서 코드르 발급 받아 필요할 때만 테스트한다.")
	void oauthLogin() {
		String code = "";
		OauthRequest oauthRequest = new OauthRequest(code);

		LoginResponse loginResponse = appleAuthService.oauthLogin(oauthRequest);

		Assertions.assertThat(loginResponse).isNotNull();
	}
}
