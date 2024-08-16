package mouda.backend.darakbangmember.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.darakbangmember.dto.response.DarakbangMemberResponses;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/darakbang")
public class DarakbangMemberController implements DarakbangMemberSwagger {

	@Override
	@GetMapping("/{darakbangId}/members")
	public ResponseEntity<RestResponse<DarakbangMemberResponses>> findAllDarakbangMembers(
		@PathVariable Long darakbangId,
		@LoginMember Member member) {
		return null;
	}
}
