package mouda.backend.notification.implement;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationWriter {

    private final MemberNotificationRepository memberNotificationRepository;

    public void saveAllMemberNotification(CommonNotification notification, List<DarakbangMember> darakbangMembers) {
        List<MemberNotificationEntity> memberNotifications = darakbangMembers.stream()
                .map(member -> createEntity(notification, member))
                .toList();

        memberNotificationRepository.saveAll(memberNotifications);
    }

    public void saveMemberNotification(CommonNotification notification, DarakbangMember darakbangMember) {
        memberNotificationRepository.save(createEntity(notification, darakbangMember));
    }

    private MemberNotificationEntity createEntity(CommonNotification notification, DarakbangMember darakbangMember) {
        return MemberNotificationEntity.builder()
                .darakbangMemberId(darakbangMember.getId())
                .type(notification.getType().name())
                .title(notification.getTitle())
                .body(notification.getBody())
                .targeturl(notification.getRedirectUrl())
                .build();
    }
}
