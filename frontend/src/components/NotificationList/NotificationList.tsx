import NotificationCard from '@_components/NotificationCard/NotificationCard';
import * as S from '@_components/NotificationList/NotificationList.style';
import { useNavigate } from 'react-router-dom';
import useNotification from '@_hooks/queries/useNotification';
import NotificationListSkeleton from './NotificationListSkeleton/NotificationListSkeleton';
import MissingFallback from '@_components/MissingFallback/MissingFallback';

export default function NotificationList() {
  const navigate = useNavigate();
  const { notifications, isLoading } = useNotification();
  const handleClickNotificationCard = (url: string) => {
    const parsedUrl = new URL(url);
    const relativePath = parsedUrl.pathname + parsedUrl.search + parsedUrl.hash;
    navigate(relativePath);
  };

  if (isLoading) {
    return <NotificationListSkeleton />;
  }
  return notifications && notifications.length > 0 ? (
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
  ) : (
    <MissingFallback text="아직 알림이 없습니다" />
  );
}
