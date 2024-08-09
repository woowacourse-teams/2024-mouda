import { Fragment } from 'react';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import PleaseLayout from '@_layouts/PleaseLayout/PleaseLayout';
import PleaseList from '@_components/PleaseList/PleaseList';
import PlusButton from '@_components/PlusButton/PlusButton';
import ROUTES from '@_constants/routes';
import { common } from '@_common/common.style';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function PleasePage() {
  const navigate = useNavigate();
  const theme = useTheme();

  return (
    <Fragment>
      <PleaseLayout>
        <PleaseLayout.Header>
          <h1 css={[common.nonScroll, theme.typography.h5]}>우아한테크코스</h1>
        </PleaseLayout.Header>

        <PleaseLayout.Main>
          <PleaseList />
        </PleaseLayout.Main>

        <PleaseLayout.HomeFixedButtonWrapper>
          <PlusButton onClick={() => navigate(ROUTES.addPlease)} />
        </PleaseLayout.HomeFixedButtonWrapper>
      </PleaseLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
