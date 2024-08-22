import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import { Fragment } from 'react';
import GET_ROUTES from '@_common/getRoutes';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import PleaseLayout from '@_layouts/PleaseLayout/PleaseLayout';
import PleaseList from '@_components/PleaseList/PleaseList';
import PlusButton from '@_components/PlusButton/PlusButton';
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
          <h1 css={[common.nonScroll, theme.typography.h5]}>
            <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
          </h1>
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
