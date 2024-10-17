package mouda.backend.auth.presentation.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.AppleOauthClient;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberWriter;

@Transactional
@Service
@RequiredArgsConstructor
public class CommonAuthService {

	private final AppleOauthClient oauthClient;
	private final MemberWriter memberWriter;

	public void withdraw(Member member) {
		if (OauthType.APPLE.equals(member.getOauthType())) {
			// token revoke
		}
		memberWriter.remove(member);
	}
}
