package mouda.backend.auth.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberWriter;

@Component
@RequiredArgsConstructor
public class JoinManager {

	private final MemberWriter memberWriter;

	public Member join(String name, OauthType oauthType, String identifier) {
		if (OauthType.KAKAO == oauthType) {
			throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.KAKAO_CANNOT_JOIN);
		}
		Member member = new Member(name, new LoginDetail(oauthType, identifier));
		return memberWriter.append(member);
	}

	public void rejoin(Member member) {
		if (member.isDeleted()) {
			member.rejoin();
			memberWriter.append(member);
		}
	}
}
