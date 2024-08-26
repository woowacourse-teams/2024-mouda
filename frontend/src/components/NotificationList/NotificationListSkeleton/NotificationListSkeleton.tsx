import NotificationCardSkeleton from '@_components/NotificationCard/NotificationCardSkeleton/NotificationCardSkeleton';
import * as S from '@_components/NotificationList/NotificationList.style';

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
