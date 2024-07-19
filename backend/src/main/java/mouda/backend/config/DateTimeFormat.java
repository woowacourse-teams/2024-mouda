package mouda.backend.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties("datetime.format")
@RequiredArgsConstructor
@Getter
public class DateTimeFormat {

	private final String date;
	private final String time;
}
