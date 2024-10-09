package mouda.backend.notification.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.implement.fcm.token.FcmTokenWriter;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmTokenService {

	private final FcmTokenWriter fcmTokenWriter;

	public void saveOrRefreshToken(DarakbangMember darakbangMember, FcmTokenRequest tokenRequest) {
		fcmTokenWriter.saveOrRefresh(darakbangMember, tokenRequest.token());
	}
}
