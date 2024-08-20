import NotificationCard from '@_components/NotificationCard/NotificationCard';
import { Notification } from '@_types/index';
import * as S from '@_components/NotificationList/NotificationList.style';
import { useNavigate } from 'react-router-dom';
import { removeBaseUrl } from '@_utils/formatters';

interface NotificationListProps {
  notifications: Notification[];
}
export default function NotificationList(props: NotificationListProps) {
  const { notifications } = props;
  const navigate = useNavigate();
  const handleClickNotificationCard = (url: string) => {
    navigate(removeBaseUrl(url));
  };
  return (
    <div css={S.cardListSection}>
      {notifications.map((notification, index) => {
        return (
          <NotificationCard
            key={notification.type + index}
            notification={notification}
            onClick={() =>
              handleClickNotificationCard(notification.redirectUrl)
            }
          ></NotificationCard>
        );
      })}
    </div>
  );
}
