package mouda.backend.darakbangmember.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageParser {

	private static final String URL_DELIMITER = "/";
	private static final int PROFILE_START_INDEX = 3;

	@Value("${aws.s3.prefix}")
	private String prefix;

	public String parse(String url) {
		log.info("ImageParser url : {}", url);

		String[] split = url.split(URL_DELIMITER);

		StringBuilder profile = new StringBuilder(prefix);
		for (int index = PROFILE_START_INDEX; index < split.length; index++) {
			profile.append(split[index]);
			if (index < split.length - 1) {
				profile.append(URL_DELIMITER);
			}
		}
		return profile.toString();
	}
}
