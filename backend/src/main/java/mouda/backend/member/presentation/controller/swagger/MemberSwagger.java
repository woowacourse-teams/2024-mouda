package mouda.backend.member.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;

public interface MemberSwagger {

	@Operation(summary = "회원 탈퇴", description = "로그인한 회원을 서비스에서 탈퇴 처리한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "회원 탈퇴 성공!"),
	})
	ResponseEntity<Void> withdraw(@LoginMember Member member);
}
