package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.presentation.response.MemberNotificationFindAllResponse;

public interface MemberNotificationSwagger {

	@Operation(summary = "회원의 개별 알림 조회", description = "알림 센터에 표시되는 회원의 개별 알림을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공!"),
	})
	ResponseEntity<RestResponse<MemberNotificationFindAllResponse>> findAllMemberNotification(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@PathVariable Long darakbangId
	);
}
