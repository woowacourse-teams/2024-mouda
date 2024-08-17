package mouda.backend.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.member.dto.response.MemberFindResponse;
import mouda.backend.member.service.MemberService;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/member")
@RequiredArgsConstructor
public class MemberController implements MemberSwagger {

	private final MemberService memberService;

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<MemberFindResponse>> findMyInfo(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	) {
		MemberFindResponse memberFindResponse = memberService.findMyInfo(member);

		return ResponseEntity.ok().body(new RestResponse<>(memberFindResponse));
	}
}
