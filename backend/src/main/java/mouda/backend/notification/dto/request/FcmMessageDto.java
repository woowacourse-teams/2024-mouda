package mouda.backend.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessageDto {
	private FcmMessageDto.Message message;

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Message {
		private FcmMessageDto.Notification notification;
		private String token;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Notification {
		private String title;
		private String body;
	}
}
