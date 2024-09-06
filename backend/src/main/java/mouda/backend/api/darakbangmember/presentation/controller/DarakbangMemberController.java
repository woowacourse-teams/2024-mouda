package mouda.backend.api.darakbangmember.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.api.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.api.common.config.argumentresolver.LoginMember;
import mouda.backend.api.common.response.RestResponse;
import mouda.backend.api.darakbangmember.business.DarakbangMemberService;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.api.darakbangmember.presentation.controller.swagger.DarakbangMemberSwagger;
import mouda.backend.core.dto.darakbang.response.DarakbangMemberResponses;
import mouda.backend.core.dto.darakbang.response.DarakbangMemberRoleResponse;
import mouda.backend.core.domain.member.Member;

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
