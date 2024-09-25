import GET_ROUTES from '@_common/getRoutes';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import PlusButton from '@_components/PlusButton/PlusButton';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import ListLayout from '@_layouts/ListLayout/ListLayout';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';
import { useNavigate } from 'react-router-dom';
import BetList from './components/BetList/BetList';

export default function BetListPage() {
  const navigate = useNavigate();

  const theme = useTheme();

  return (
    <Fragment>
      <ListLayout>
        <ListLayout.Header>
          <h1 css={[theme.typography.h5]}>안내면진거</h1>
        </ListLayout.Header>

        <ListLayout.Main>
          <BetList />
        </ListLayout.Main>

        <ListLayout.PlusButtonWrapper>
          <PlusButton
            onClick={() => navigate(GET_ROUTES.nowDarakbang.betCreation())}
          />
        </ListLayout.PlusButtonWrapper>
      </ListLayout>

      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
