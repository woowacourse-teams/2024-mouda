import NotificationCard from '@_components/NotificationCard/NotificationCard';
import { Notification } from '@_types/index';
import * as S from '@_components/NotificationList/NotificationList.style';

interface NotificationListProps {
  notifications: Notification[];
}
export default function NotificationList(props: NotificationListProps) {
  const { notifications } = props;
  return (
    <div css={S.cardListSection}>
      {notifications.map((notification, index) => {
        return (
          <NotificationCard
            key={notification.type + index}
            notification={notification}
          ></NotificationCard>
        );
      })}
    </div>
  );
}
