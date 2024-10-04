import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import { Fragment } from 'react';
import GET_ROUTES from '@_common/getRoutes';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import PleaseLayout from '@_layouts/PleaseLayout/PleaseLayout';
import PleaseList from '@_pages/Please/PleasePage/components/PleaseList/PleaseList';
import PlusButton from '@_components/PlusButton/PlusButton';
import RefreshButton from '@_components/RefreshButton/RefreshButton';
import { common } from '@_common/common.style';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useTheme } from '@emotion/react';

export default function PleasePage() {
  const { darakbangName } = useNowDarakbangName();

  const navigate = useNavigate();
  const theme = useTheme();

  return (
    <Fragment>
      <PleaseLayout>
        <PleaseLayout.Header>
          <PleaseLayout.Header.Left>
            <h1 css={[common.nonDrag, theme.typography.h5]}>
              <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
            </h1>
          </PleaseLayout.Header.Left>
          <PleaseLayout.Header.Right>
            <RefreshButton />
          </PleaseLayout.Header.Right>
        </PleaseLayout.Header>

        <PleaseLayout.Main>
          <PleaseList />
        </PleaseLayout.Main>

        <PleaseLayout.HomeFixedButtonWrapper>
          <PlusButton
            onClick={() => navigate(GET_ROUTES.nowDarakbang.addPlease())}
          />
        </PleaseLayout.HomeFixedButtonWrapper>
      </PleaseLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
