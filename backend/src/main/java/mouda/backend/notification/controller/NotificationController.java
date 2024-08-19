package mouda.backend.notification.controller;

import mouda.backend.notification.dto.request.FcmTokenRefreshRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/v1/darakbang/{darakbangId}/notification")
@RequiredArgsConstructor
public class NotificationController implements NotificationSwagger {

	private final NotificationService notificationService;

	@Override
	@PostMapping("/register")
	public ResponseEntity<Void> registerFcmToken(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody FcmTokenSaveRequest fcmTokenSaveRequest
	) {
		notificationService.registerFcmToken(member.getId(), fcmTokenSaveRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/refresh")
	public ResponseEntity<Void> refreshFcmToken(@RequestBody FcmTokenRefreshRequest fcmTokenRefreshRequest) {
		notificationService.refreshFcmToken(fcmTokenRefreshRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<NotificationFindAllResponses>> findAllMyNotification(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	) {
		NotificationFindAllResponses responses = notificationService.findAllMyNotifications(member);

		return ResponseEntity.ok().body(new RestResponse<>(responses));
	}
}
