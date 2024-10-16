import GET_ROUTES from '@_common/getRoutes';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import PlusButton from '@_components/PlusButton/PlusButton';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';
import { useNavigate } from 'react-router-dom';
import BetList from './components/BetList/BetList';
import DefaultPageLayout from '@_layouts/DefaultPageLayout/DefaultPageLayout';
import RefreshButton from '@_components/RefreshButton/RefreshButton';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { common } from '@_common/common.style';

export default function BetListPage() {
  const navigate = useNavigate();
  const { darakbangName } = useNowDarakbangName();

  const theme = useTheme();

  return (
    <Fragment>
      <DefaultPageLayout>
        <DefaultPageLayout.DoubleTriHeader>
          <DefaultPageLayout.DoubleTriHeader.Top>
            <DefaultPageLayout.DoubleTriHeader.Top.Left>
              <h1 css={[common.nonScroll, theme.typography.h5]}>
                <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
              </h1>
            </DefaultPageLayout.DoubleTriHeader.Top.Left>
            <DefaultPageLayout.DoubleTriHeader.Top.Right>
              <RefreshButton />
            </DefaultPageLayout.DoubleTriHeader.Top.Right>
          </DefaultPageLayout.DoubleTriHeader.Top>
        </DefaultPageLayout.DoubleTriHeader>

        <DefaultPageLayout.Main>
          <BetList />
        </DefaultPageLayout.Main>

        <DefaultPageLayout.ListPageFixedButtonWrapper>
          <PlusButton
            onClick={() => navigate(GET_ROUTES.nowDarakbang.betCreation())}
          />
        </DefaultPageLayout.ListPageFixedButtonWrapper>
      </DefaultPageLayout>

      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
