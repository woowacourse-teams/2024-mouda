package mouda.backend.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "url")
@Getter
@RequiredArgsConstructor
public class UrlConfig {

	private final String base;
	private final String moim;
	private final String chat;
	private final String chatroom;
}
