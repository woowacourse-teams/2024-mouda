package mouda.backend.notification.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.implement.fcm.token.FcmTokenWriter;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmTokenService {

	private final FcmTokenWriter fcmTokenWriter;

	@Transactional
	public void saveOrRefreshToken(Member member, FcmTokenRequest tokenRequest) {
		fcmTokenWriter.saveOrRefresh(member, tokenRequest.token());
	}
}
