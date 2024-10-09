package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.presentation.request.ChatSubscriptionRequest;
import mouda.backend.notification.presentation.response.SubscriptionResponse;

public interface SubscriptionSwagger {

	@Operation(summary = "모임 생성시 알림 여부 조회", description = "모임 생성에 대한 알림 허용 여부를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공"),
	})
	ResponseEntity<SubscriptionResponse> readMoimCreateSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "모임 생성 알림 여부 수정", description = "알림 허용상태면 비허용으로, 비허용 상태면 허용 상태로 변경한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "변경 성공"),
	})
	ResponseEntity<Void> changeMoimCreateSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "특정 채팅방에 대한 알림 여부 조회", description = "특정 채팅방에 대한 알림 허용 여부를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공"),
	})
	ResponseEntity<SubscriptionResponse> readSpecificChatRoomSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody ChatSubscriptionRequest chatSubscriptionRequest
	);

	@Operation(summary = "특정 채팅방에 대한 알림 여부 수정", description = "알림 허용상태면 비허용으로, 비허용 상태면 허용 상태로 변경한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "변경 성공"),
	})
	ResponseEntity<Void> changeChatRoomSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody ChatSubscriptionRequest chatSubscriptionRequest
	);
}
