import { Notification } from '@_types/index';
import * as S from '@_components/NotificationCard/NotificationCard.style';
import { useTheme } from '@emotion/react';
interface NotificationCardProps {
  notification: Notification;
}
export default function NotificationCard(props: NotificationCardProps) {
  const { notification } = props;
  const theme = useTheme();
  return (
    <div css={S.CardBox}>
      <div>{notification.type}</div>
      <div css={S.TextInfoBox}>
        <div css={S.Title({ theme })}>{notification.message}</div>
        <div css={S.SubTitle({ theme })}>{notification.createdAt}</div>
      </div>
    </div>
  );
}
