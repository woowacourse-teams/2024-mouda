package mouda.backend.darakbangmember.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageParserTest {

	@Autowired
	private ImageParser imageParser;

	@DisplayName("이미지 URL이 입력되면 이를 파싱하여 다운로드 가능한 URL로 변경한다.")
	@Test
	void parse() {
		String url = "https://techcourse-project-2024.s3.ap-northeast-2.amazonaws.com/mouda/dev/dev-profile/d1ed4b27-9cbc-43d6-b972-55f9f3977a77-profile.png.jpg";

		String profile = imageParser.parse(url);

		String expected = "https://dev.mouda.site/dev-profile/d1ed4b27-9cbc-43d6-b972-55f9f3977a77-profile.png.jpg";
		assertThat(profile).isEqualTo(expected);
	}
}
