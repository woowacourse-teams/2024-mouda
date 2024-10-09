import BackLogo from '@_common/assets/back.svg';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';

import RefreshButton from '@_components/RefreshButton/RefreshButton';
import { useNavigate } from 'react-router-dom';

export default function SettingPage() {
  const navigate = useNavigate();
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbang.main())}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          <RefreshButton />
        </InformationLayout.Header.Right>
        <InformationLayout.Header.Center>설정</InformationLayout.Header.Center>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer></InformationLayout.ContentContainer>
    </InformationLayout>
  );
}
