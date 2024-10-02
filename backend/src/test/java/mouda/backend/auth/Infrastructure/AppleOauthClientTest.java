package mouda.backend.auth.Infrastructure;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppleOauthClientTest {

	@Autowired
	private AppleOauthClient appleOauthClient;

	@DisplayName("애플 로그인 시 애플 서버에게 토큰을 요청한다.")
	@Test
	@Disabled("실제 애플 서버에 요청을 보내는 테스트이다. 프론트 서버를 켜서 코드르 발급 받아 필요할 때만 테스트한다.")
	void getIdToken() {
		String code = "";

		String idToken = appleOauthClient.getIdToken(code);
		assertThat(idToken).isNotNull();
	}
}
