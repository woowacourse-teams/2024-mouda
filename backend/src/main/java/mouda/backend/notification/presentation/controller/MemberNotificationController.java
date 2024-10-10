package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.business.MemberNotificationService;
import mouda.backend.notification.presentation.response.MemberNotificationFindAllResponse;

@RestController
@RequiredArgsConstructor
public class MemberNotificationController implements MemberNotificationSwagger {

	private final MemberNotificationService memberNotificationService;

	// todo: 이제 darakbangId로 조회할 필요가 없음. DarakbangMember의 id로 조회. -> API 명세 수정 필요
	@GetMapping("/v1/darakbang/{darakbangId}/notification/mine")
	@Override
	public ResponseEntity<RestResponse<MemberNotificationFindAllResponse>> findAllMemberNotification(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@PathVariable Long darakbangId
	) {
		MemberNotificationFindAllResponse response = memberNotificationService.findAllMemberNotification(
			darakbangMember);
		return ResponseEntity.ok(new RestResponse<>(response));
	}
}
