package mouda.backend.member.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.member.business.MemberService;
import mouda.backend.member.domain.Member;
import mouda.backend.member.presentation.controller.swagger.MemberSwagger;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberSwagger {

	private final MemberService memberService;

	@Override
	@DeleteMapping
	public ResponseEntity<Void> withdraw(@LoginMember Member member) {
		memberService.withdraw(member);

		return ResponseEntity.ok().build();
	}
}
