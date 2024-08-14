package mouda.backend.notification.service;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.Notification;
import mouda.backend.notification.dto.request.FcmMessageRequest;
import mouda.backend.notification.dto.request.FcmSendRequest;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.dto.response.FcmMessageResponse;
import mouda.backend.notification.repository.NotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private static final String API_URL = "https://fcm.googleapis.com/v1/projects/mouda-message/messages:send";

	private final NotificationRepository notificationRepository;
	private final RestClient restClient = RestClient.create();

	public void sendMessage(FcmSendRequest fcmSendRequest) throws IOException {
		log.info("send message start : body = " + fcmSendRequest.body());
		FcmMessageRequest.Message message = FcmMessageRequest.Message.builder()
			.notification(new FcmMessageRequest.Notification(fcmSendRequest.title(), fcmSendRequest.body()))
			.build();
		FcmMessageRequest fcmMessageRequest = FcmMessageRequest.builder().message(message).build();

		log.info("call api");
		FcmMessageResponse response = restClient.post()
			.uri(API_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.body(fcmMessageRequest)
			.retrieve()
			.body(FcmMessageResponse.class);
		log.info("success count : %d", response.success());
	}

	public void saveFcmToken(long memberId, FcmTokenSaveRequest fcmTokenSaveRequest) {
		notificationRepository.save(new Notification(memberId, fcmTokenSaveRequest.token()));
	}
}
