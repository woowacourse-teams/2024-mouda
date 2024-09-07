package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.response.RestResponse;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.presentation.request.FcmTokenSaveRequest;
import mouda.backend.notification.presentation.response.NotificationFindAllResponses;
import mouda.backend.notification.presentation.controller.swagger.NotificationSwagger;
import mouda.backend.notification.business.NotificationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class NotificationController implements NotificationSwagger {

	private final NotificationService notificationService;

	@Override
	@PostMapping("/v1/notification/register")
	public ResponseEntity<Void> registerFcmToken(
		@LoginMember Member member,
		@Valid @RequestBody FcmTokenSaveRequest fcmTokenSaveRequest
	) {
		notificationService.registerFcmToken(member.getId(), fcmTokenSaveRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/v1/darakbang/{darakbangId}/notification/mine")
	public ResponseEntity<RestResponse<NotificationFindAllResponses>> findAllMyNotification(
		@LoginMember Member member,
		@PathVariable Long darakbangId
	) {
		NotificationFindAllResponses responses = notificationService.findAllMyNotifications(member, darakbangId);

		return ResponseEntity.ok().body(new RestResponse<>(responses));
	}
}
