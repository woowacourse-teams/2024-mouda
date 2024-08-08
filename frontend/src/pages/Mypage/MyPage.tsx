import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import MineInfoCard from '@_components/MineInfoCard/MineInfoCard';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useMyInfo from '@_hooks/queries/useMyInfo';
import { useTheme } from '@emotion/react';

export default function MyPage() {
  const { myInfo, isLoading } = useMyInfo();
  const theme = useTheme();
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
          <span css={[[theme.typography.h5, common.nonScroll]]}>
            우아한테크코스
          </span>
        </InformationLayout.Header.Left>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <MineInfoCard
          nickname={myInfo.nickname}
          profile={myInfo.profile}
        ></MineInfoCard>
      </InformationLayout.ContentContainer>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </InformationLayout>
  );
}
