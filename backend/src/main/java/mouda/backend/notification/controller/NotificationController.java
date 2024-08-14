package mouda.backend.notification.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.dto.request.FcmSendRequest;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.service.NotificationService;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/send")
	public void saveFcmToken(@LoginMember Member member, @RequestBody FcmTokenSaveRequest fcmTokenSaveRequest) {
		notificationService.saveFcmToken(member.getId(), fcmTokenSaveRequest);
	}

	@PostMapping("/message")
	public void test(@RequestBody FcmSendRequest fcmSendRequest) throws IOException {
		notificationService.sendMessage(fcmSendRequest);
	}
}
