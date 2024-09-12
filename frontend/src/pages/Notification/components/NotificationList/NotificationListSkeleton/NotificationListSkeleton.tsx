import NotificationCardSkeleton from './NotificationCardSkeleton/NotificationCardSkeleton';
import * as S from '@_pages/Notification/components/NotificationList/NotificationList.style';

export default function NotificationListSkeleton() {
  return (
    <div css={S.cardListSection}>
      <NotificationCardSkeleton />
      <NotificationCardSkeleton />
      <NotificationCardSkeleton />
      <NotificationCardSkeleton />
    </div>
  );
}
