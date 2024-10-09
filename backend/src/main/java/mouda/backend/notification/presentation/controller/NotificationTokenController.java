package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.business.FcmTokenService;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

@RestController
@RequiredArgsConstructor
public class NotificationTokenController implements NotificationTokenSwagger {

	private final FcmTokenService fcmTokenService;

	@PostMapping("/v1/notification/register")
	public ResponseEntity<Void> saveOrRefreshToken(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody FcmTokenRequest tokenRequest
	) {
		fcmTokenService.saveOrRefreshToken(darakbangMember, tokenRequest);

		return ResponseEntity.ok().build();
	}
}
