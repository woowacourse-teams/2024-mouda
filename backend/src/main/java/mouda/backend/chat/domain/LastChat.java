package mouda.backend.chat.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class LastChat {

	private final LocalDateTime dateTime;
	private final String content;

	public LastChat(LocalDateTime dateTime, String content) {
		this.dateTime = dateTime;
		this.content = content;
	}

	public static LastChat empty() {
		return new LastChat(null, "");
	}
}
