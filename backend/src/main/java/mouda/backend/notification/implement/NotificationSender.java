package mouda.backend.notification.implement;

import java.util.List;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.CommonNotification;

public interface NotificationSender {

    void sendNotification(CommonNotification notification, DarakbangMember receiver);

    void sendNotification(CommonNotification notification, List<DarakbangMember> receivers);

    void sendMoimCreateNotification(CommonNotification notification, List<DarakbangMember> receivers);

    void sendChatNotification(CommonNotification notification, List<DarakbangMember> receivers, long chatRoomId);
}
