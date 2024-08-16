import ROUTES from '@_constants/routes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import BackLogo from '@_common/assets/back.svg';
import { useNavigate } from 'react-router-dom';
import NotificationList from '@_components/NotificationList/NotificationList';
export default function NotificationPage() {
  const navigate = useNavigate();
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(ROUTES.main)}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Center>알림</InformationLayout.Header.Center>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <NotificationList
          notifications={[
            {
              type: '아무거나',
              title: '아무거나',
              time: '아무거나',
            },
            {
              type: '아무거나',
              title: '아무거나',
              time: '아무거나',
            },
          ]}
        />
      </InformationLayout.ContentContainer>
    </InformationLayout>
  );
}
