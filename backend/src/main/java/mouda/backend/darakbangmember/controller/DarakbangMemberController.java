package mouda.backend.darakbangmember.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.dto.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.dto.response.DarakbangMemberRoleResponse;
import mouda.backend.darakbangmember.service.DarakbangMemberService;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/darakbang")
@RequiredArgsConstructor
public class DarakbangMemberController implements DarakbangMemberSwagger {

	private final DarakbangMemberService darakbangMemberService;

	@Override
	@GetMapping("/{darakbangId}/members")
	public ResponseEntity<RestResponse<DarakbangMemberResponses>> findAllDarakbangMembers(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	) {
		DarakbangMemberResponses responses = darakbangMemberService.findAllDarakbangMembers(darakbangId, member);

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
}
