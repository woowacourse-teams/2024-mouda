package mouda.backend.notification.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;

@Component
@RequiredArgsConstructor
public class MemberNotificationFinder {

	private final MemberNotificationRepository memberNotificationRepository;

	public List<MemberNotification> findAll(Member member, long darakbangId) {
		return memberNotificationRepository.findAllByMemberIdAndDarakbangIdOrderByIdDesc(
			member.getId(), darakbangId);
	}
}
