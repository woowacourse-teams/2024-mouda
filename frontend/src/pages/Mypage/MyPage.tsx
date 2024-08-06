import { useNavigate } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';

import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import ROUTES from '@_constants/routes';
import MineInfoCard from '@_components/MineInfoCard/MineInfoCard';
import useMyInfo from '@_hooks/queries/useMyInfo';

export default function MyPage() {
  const navigate = useNavigate();
  const { myInfo, isLoading } = useMyInfo();
  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!myInfo) {
    return <div>Failed to load my information.</div>;
  }
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(ROUTES.main)}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Center>
          마이페이지
        </InformationLayout.Header.Center>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <MineInfoCard
          nickname={myInfo.nickname}
          profile={myInfo.profile}
        ></MineInfoCard>
      </InformationLayout.ContentContainer>
    </InformationLayout>
  );
}
