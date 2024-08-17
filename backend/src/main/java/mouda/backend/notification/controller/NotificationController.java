package mouda.backend.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.dto.response.NotificationFindAllResponses;
import mouda.backend.notification.service.NotificationService;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController implements NotificationSwagger {

	private final NotificationService notificationService;

	@Override
	@PostMapping("/register")
	public ResponseEntity<Void> registerFcmToken(
		@LoginMember Member member,
		@Valid @RequestBody FcmTokenSaveRequest fcmTokenSaveRequest
	) {
		notificationService.registerFcmToken(member.getId(), fcmTokenSaveRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<NotificationFindAllResponses>> findAllMyNotification(
		@LoginMember Member member) {
		NotificationFindAllResponses responses = notificationService.findAllMyNotifications(member);

		return ResponseEntity.ok().body(new RestResponse<>(responses));
	}
}
