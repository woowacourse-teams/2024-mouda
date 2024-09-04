package mouda.backend.member.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.business.MemberService;
import mouda.backend.member.presentation.controller.swagger.MemberSwagger;
import mouda.backend.member.presentation.response.MemberFindResponse;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/member")
@RequiredArgsConstructor
public class MemberController implements MemberSwagger {

	private final MemberService memberService;

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<MemberFindResponse>> findMyInfo(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	) {
		MemberFindResponse memberFindResponse = memberService.findMyInfo(member);

		return ResponseEntity.ok().body(new RestResponse<>(memberFindResponse));
	}
}
