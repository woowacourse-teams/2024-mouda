package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.business.FcmTokenService;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

@RestController
@RequiredArgsConstructor
public class NotificationTokenController implements NotificationTokenSwagger {

	private final FcmTokenService fcmTokenService;

	@PostMapping("/v1/notification/register")
	public ResponseEntity<Void> saveOrRefreshToken(
		@LoginMember Member member,
		@RequestBody FcmTokenRequest tokenRequest
	) {
		fcmTokenService.saveOrRefreshToken(member, tokenRequest);

		return ResponseEntity.ok().build();
	}
}
