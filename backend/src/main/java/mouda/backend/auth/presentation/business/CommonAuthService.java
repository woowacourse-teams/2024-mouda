package mouda.backend.auth.presentation.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.Infrastructure.AppleOauthClient;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberWriter;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CommonAuthService {

	private final AppleOauthClient oauthClient;
	private final MemberWriter memberWriter;

	public void withdraw(Member member) {
		if (OauthType.APPLE.equals(member.getOauthType())) {
			log.info("revoke apple user");
			oauthClient.revoke(member.getRefreshToken());
		}
		memberWriter.remove(member);
	}
}
