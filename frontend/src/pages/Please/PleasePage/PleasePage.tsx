import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import { Fragment } from 'react';
import GET_ROUTES from '@_common/getRoutes';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import PleaseList from '@_pages/Please/PleasePage/components/PleaseList/PleaseList';
import PlusButton from '@_components/PlusButton/PlusButton';
import { common } from '@_common/common.style';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useTheme } from '@emotion/react';
import RefreshButton from '@_components/RefreshButton/RefreshButton';
import ListPageLayout from '@_layouts/ListPageLayout/ListPageLayout';

export default function PleasePage() {
  const { darakbangName } = useNowDarakbangName();

  const navigate = useNavigate();
  const theme = useTheme();

  return (
    <Fragment>
      <ListPageLayout>
        <ListPageLayout.TriHeader>
          <ListPageLayout.TriHeader.Left>
            <h1 css={[common.nonScroll, theme.typography.h5]}>
              <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
            </h1>
          </ListPageLayout.TriHeader.Left>
          <ListPageLayout.TriHeader.Right>
            <RefreshButton />
          </ListPageLayout.TriHeader.Right>
        </ListPageLayout.TriHeader>

        <ListPageLayout.Main>
          <PleaseList />
        </ListPageLayout.Main>

        <ListPageLayout.ListPageFixedButtonWrapper>
          <PlusButton
            onClick={() => navigate(GET_ROUTES.nowDarakbang.addPlease())}
          />
        </ListPageLayout.ListPageFixedButtonWrapper>
      </ListPageLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
