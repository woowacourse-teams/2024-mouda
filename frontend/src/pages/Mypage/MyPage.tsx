import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useMyInfo from '@_hooks/queries/useMyInfo';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useTheme } from '@emotion/react';
import MineInfoCard from './components/MineInfoCard/MineInfoCard';
import DefaultPageLayout from '@_layouts/DefaultPageLayout/DefaultPageLayout';
import { Fragment } from 'react';

export default function MyPage() {
  const { myInfo, isLoading } = useMyInfo();
  const { darakbangName } = useNowDarakbangName();

  const theme = useTheme();

  if (isLoading) {
    return <div>Loading...</div>;
  }
  return (
    <Fragment>
      <DefaultPageLayout>
        <DefaultPageLayout.TriHeader>
          <DefaultPageLayout.TriHeader.Left>
            <span css={[[theme.typography.h5, common.nonScroll]]}>
              <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
            </span>
          </DefaultPageLayout.TriHeader.Left>
        </DefaultPageLayout.TriHeader>
        <DefaultPageLayout.Main>
          {myInfo && (
            <MineInfoCard
              nickname={myInfo.nickname}
              profile={myInfo.profile}
            ></MineInfoCard>
          )}
        </DefaultPageLayout.Main>
      </DefaultPageLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
