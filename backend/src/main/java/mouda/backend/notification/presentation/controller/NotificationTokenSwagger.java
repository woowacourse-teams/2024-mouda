package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

public interface NotificationTokenSwagger {

	@Operation(summary = "FCM 토큰 등록", description = "알림 허용시 FCM 토큰을 등록하고, 이미 등록된 토큰이면 갱신한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "등록(갱신) 성공!"),
	})
	ResponseEntity<Void> saveOrRefreshToken(
		@LoginMember Member member,
		@RequestBody FcmTokenRequest tokenRequest
	);
}
