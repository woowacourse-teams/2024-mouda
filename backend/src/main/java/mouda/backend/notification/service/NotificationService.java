package mouda.backend.notification.service;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.Notification;
import mouda.backend.notification.dto.request.FcmMessageDto;
import mouda.backend.notification.dto.request.FcmSendRequest;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.repository.NotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private static final String API_URL = "https://fcm.googleapis.com/v1/projects/test-19b91/messages:send";

	private final NotificationRepository notificationRepository;
	private final RestClient restClient = RestClient.create();

	public void sendMessage(FcmSendRequest fcmSendRequest) throws IOException {
		FcmMessageDto.Message message = FcmMessageDto.Message.builder()
			.notification(new FcmMessageDto.Notification(fcmSendRequest.title(), fcmSendRequest.body()))
			.build();
		FcmMessageDto fcmMessageDto = FcmMessageDto.builder().message(message).build();

		restClient.post()
			.uri(API_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.body(fcmMessageDto)
			.retrieve()
			.toBodilessEntity();

		// String response = FirebaseMessaging.getInstance().sendAsync(message).get();
		// log.info("Sent message: " + response);
	}

	public void saveFcmToken(long memberId, FcmTokenSaveRequest fcmTokenSaveRequest) {
		notificationRepository.save(new Notification(memberId, fcmTokenSaveRequest.token()));
	}
}
