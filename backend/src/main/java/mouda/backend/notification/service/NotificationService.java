package mouda.backend.notification.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.FirebaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.dto.response.NotificationFindAllResponse;
import mouda.backend.notification.dto.response.NotificationFindAllResponses;
import mouda.backend.notification.repository.FcmTokenRepository;
import mouda.backend.notification.repository.MemberNotificationRepository;
import mouda.backend.notification.repository.MoudaNotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = FirebaseException.class)
public class NotificationService {

	private final FcmTokenRepository fcmTokenRepository;
	private final MoudaNotificationRepository moudaNotificationRepository;
	private final MemberNotificationRepository memberNotificationRepository;
	private final FcmService fcmService;

	public void registerFcmToken(long memberId, FcmTokenSaveRequest fcmTokenSaveRequest) {
		fcmService.registerToken(memberId, fcmTokenSaveRequest.token());
	}

	public void notifyToMember(MoudaNotification moudaNotification, Long memberId, Long darakbangId) {
		MoudaNotification notification = moudaNotificationRepository.save(moudaNotification);

		if (notification.getType() != NotificationType.NEW_CHAT) {
			memberNotificationRepository.save(MemberNotification.builder()
				.memberId(memberId)
				.darakbangId(darakbangId)
				.moudaNotification(notification)
				.build());
		}

		List<String> tokens = fcmTokenRepository.findAllTokenByMemberId(memberId);
		fcmService.sendNotification(notification, tokens);
	}

	public void notifyToAllMembers(MoudaNotification moudaNotification, Long darakbangId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId();

		notifyToMembers(moudaNotification, allMemberId, darakbangId);
	}

	public void notifyToAllExceptMember(MoudaNotification moudaNotification, Long exceptMemberId, Long darakbangId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !Objects.equals(memberId, exceptMemberId))
			.toList();

		notifyToMembers(moudaNotification, allMemberId, darakbangId);
	}

	public void notifyToAllExceptMember(MoudaNotification moudaNotification, List<Long> exceptMemberIds,
		Long darakbangId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !exceptMemberIds.contains(memberId))
			.toList();

		notifyToMembers(moudaNotification, allMemberId, darakbangId);
	}

	public void notifyToMembers(MoudaNotification moudaNotification, List<Long> memberIds, Long darakbangId) {
		MoudaNotification notification = moudaNotificationRepository.save(moudaNotification);

		if (notification.getType() != NotificationType.NEW_CHAT) {
			memberNotificationRepository.saveAll(memberIds.stream()
				.map(memberId -> MemberNotification.builder()
					.memberId(memberId)
					.darakbangId(darakbangId)
					.moudaNotification(notification)
					.build())
				.toList());
		}

		List<String> tokens = fcmTokenRepository.findAllTokenByMemberIds(memberIds);
		fcmService.sendNotification(notification, tokens);
	}

	public NotificationFindAllResponses findAllMyNotifications(Member member, Long darakbangId) {
		List<NotificationFindAllResponse> responses = memberNotificationRepository.findAllByMemberIdAndDarakbangIdOrderByIdDesc(
				member.getId(), darakbangId)
			.stream()
			.map(MemberNotification::getMoudaNotification)
			.map(NotificationFindAllResponse::from)
			.toList();

		return new NotificationFindAllResponses(responses);
	}
}
