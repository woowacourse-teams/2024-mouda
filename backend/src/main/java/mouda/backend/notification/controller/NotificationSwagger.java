package mouda.backend.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.dto.response.NotificationFindAllResponses;

public interface NotificationSwagger {

	@Operation(summary = "FCM 토큰을 저장합니다.", description = "알림 허용시 FCM 토큰을 저장합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "FCM 토큰 저장 성공!")
	})
	ResponseEntity<Void> registerFcmToken(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody FcmTokenSaveRequest fcmTokenSaveRequest
	);

	@Operation(summary = "모든 알림 조회", description = "회원의 모든 알림을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "알림 조회 성공!")
	})
	ResponseEntity<RestResponse<NotificationFindAllResponses>> findAllMyNotification(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	);
}
