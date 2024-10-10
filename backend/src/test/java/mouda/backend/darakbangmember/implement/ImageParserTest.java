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
		String url = "https://techcourse-project-2024.s3.ap-northeast-2.amazonaws.com/7c60f9f0-a8ef-4209-8768-9c9a010bbc24.png";

		String profile = imageParser.parse(url);

		String expected = "https://dev.mouda.site/profile/7c60f9f0-a8ef-4209-8768-9c9a010bbc24.png";
		assertThat(profile).isEqualTo(expected);
	}
}
