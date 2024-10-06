package mouda.backend.notification.implement.fcm.token;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.implement.subscription.SubscriptionFinder;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;

@Component
@RequiredArgsConstructor
public class FcmTokenFinder {

	private final SubscriptionFinder subscriptionFinder;
	private final FcmTokenRepository fcmTokenRepository;

	public List<String> findAllTokensByMember(DarakbangMember darakbangMember) {
		return fcmTokenRepository.findAllByMemberId(darakbangMember.getMemberId()).stream()
			.map(FcmTokenEntity::getToken)
			.toList();
	}

	public List<String> findAllTokensByMembers(List<DarakbangMember> darakbangMembers) {
		return darakbangMembers.stream()
			.flatMap(darakbangMember -> findAllTokensByMember(darakbangMember).stream())
			.toList();
	}

	public List<String> findAllTokensByMembersSubscribedMoimCreate(List<DarakbangMember> darakbangMembers) {
		return darakbangMembers.stream()
			.filter(darakbangMember -> subscriptionFinder.isSubscribedMoimCreate(darakbangMember.getMemberId()))
			.flatMap(darakbangMember -> findAllTokensByMember(darakbangMember).stream())
			.toList();
	}

	public List<String> findAllTokensByMembersSubscribedChat(long chatRoomId, List<DarakbangMember> darakbangMembers) {
		return darakbangMembers.stream()
			.filter(darakbangMember -> subscriptionFinder.isSubscribedChat(chatRoomId, darakbangMember.getMemberId(),
				darakbangMember.getDarakbang().getId()))
			.flatMap(darakbangMember -> findAllTokensByMember(darakbangMember).stream())
			.toList();
	}

}
