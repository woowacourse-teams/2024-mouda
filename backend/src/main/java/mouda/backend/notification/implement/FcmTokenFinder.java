package mouda.backend.notification.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.infrastructure.FcmTokenRepository;

@Component
@RequiredArgsConstructor
public class FcmTokenFinder {

	private final FcmTokenRepository fcmTokenRepository;

	public List<String> findAllByRecipientId(long recipientId) {
		return fcmTokenRepository.findAllTokenByMemberId(recipientId);
	}

	public List<String> findAllByRecipients(List<Long> recipients) {
		return fcmTokenRepository.findAllTokenByMemberIds(recipients);
	}
}
