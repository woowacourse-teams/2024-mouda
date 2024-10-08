package mouda.backend.darakbangmember.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.business.DarakbangMemberService;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.presentation.controller.swagger.DarakbangMemberSwagger;
import mouda.backend.darakbangmember.presentation.request.DarakbangMemberInfoRequest;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.presentation.response.DarakbangMemberInfoResponse;

@RestController
@RequestMapping("/v1/darakbang")
@RequiredArgsConstructor
public class DarakbangMemberController implements DarakbangMemberSwagger {

	private final DarakbangMemberService darakbangMemberService;

	@Override
	@GetMapping("/{darakbangId}/members")
	public ResponseEntity<RestResponse<DarakbangMemberResponses>> findAllDarakbangMembers(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		DarakbangMemberResponses responses = darakbangMemberService.findAllDarakbangMembers(darakbangId,
			darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(responses));
	}

	@Override
	@GetMapping("/{darakbangId}/role")
	public ResponseEntity<RestResponse<DarakbangMemberRoleResponse>> findDarakbangMemberRole(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	) {
		DarakbangMemberRoleResponse response = darakbangMemberService.findDarakbangMemberRole(darakbangId, member);

		return ResponseEntity.ok(new RestResponse<>(response));
	}

	@Override
	@GetMapping("/{darakbangId}/member/mine")
	public ResponseEntity<RestResponse<DarakbangMemberInfoResponse>> findMyInfo(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		DarakbangMemberInfoResponse darakbangMemberInfoResponse = darakbangMemberService.findMyInfo(darakbangMember);

		return ResponseEntity.ok().body(new RestResponse<>(darakbangMemberInfoResponse));
	}

	@Override
	@PatchMapping("/{darakbangId}/member/mine")
	public ResponseEntity<Void> updateMyInfo(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody DarakbangMemberInfoRequest request
	) {
		darakbangMemberService.updateMyInfo(darakbangMember, request);

		return ResponseEntity.ok().build();
	}
}
