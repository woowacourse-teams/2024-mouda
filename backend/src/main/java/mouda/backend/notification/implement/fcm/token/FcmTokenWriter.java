package mouda.backend.notification.implement.fcm.token;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

@Component
@RequiredArgsConstructor
public class FcmTokenWriter {

	private final FcmTokenRepository fcmTokenRepository;

	public void registerToken(DarakbangMember darakbangMember, FcmTokenRequest tokenRequest) {
		String token = tokenRequest.token();
		fcmTokenRepository.findByToken(token).ifPresentOrElse(

		);
	}
}
