import BackLogo from '@_common/assets/back.svg';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import NotificationList from '@_components/NotificationList/NotificationList';
import { useNavigate } from 'react-router-dom';
import useNotification from '@_hooks/queries/useNotification';

export default function NotificationPage() {
  const navigate = useNavigate();
  const { notifications, isLoading } = useNotification();
  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (!notifications) {
    return <div>Failed to load my information.</div>;
  }
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbangMain())}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Center>알림</InformationLayout.Header.Center>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <NotificationList notifications={notifications} />
      </InformationLayout.ContentContainer>
    </InformationLayout>
  );
}
