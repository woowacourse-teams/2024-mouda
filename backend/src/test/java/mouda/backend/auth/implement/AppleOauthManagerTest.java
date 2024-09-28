package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppleOauthManagerTest {

	@Autowired
	private AppleOauthManager appleOauthManager;

	@DisplayName("애플 서버에서 받아온 Identity Token으로 사용자 정보를 추출한다.")
	@Test
	@Disabled("실제 애플 서버에 요청을 보내는 테스트이다. 프론트 서버를 켜서 코드르 발급 받아 필요할 때만 테스트한다.")
	void getUserInfo() {
		String code = "c07ae964bc0224295afc568b7a4f22a1c.0.rurr.iktmmb0wYX7PYHFdLpWvvg";

		String socialLoginId = appleOauthManager.getAppleUserInfo(code);
		assertThat(socialLoginId).isNotNull();
	}
}
