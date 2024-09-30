import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useMyInfo from '@_hooks/queries/useMyInfo';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useTheme } from '@emotion/react';
import MineInfoCard from './components/MineInfoCard/MineInfoCard';
import ListPageLayout from '@_layouts/ListPageLayout/ListPageLayout';
import { Fragment } from 'react';

export default function MyPage() {
  const { myInfo, isLoading } = useMyInfo();
  const theme = useTheme();
  const { darakbangName } = useNowDarakbangName();
  if (isLoading) {
    return <div>Loading...</div>;
  }
  return (
    <Fragment>
      <ListPageLayout>
        <ListPageLayout.TriHeader>
          <ListPageLayout.TriHeader.Left>
            <span css={[[theme.typography.h5, common.nonScroll]]}>
              <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
            </span>
          </ListPageLayout.TriHeader.Left>
        </ListPageLayout.TriHeader>
        <ListPageLayout.Main>
          {myInfo && (
            <MineInfoCard
              nickname={myInfo.nickname}
              profile={myInfo.profile}
            ></MineInfoCard>
          )}
        </ListPageLayout.Main>
      </ListPageLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
