import NavigationBar from '@_components/NavigationBar/NavigationBar';
import PleaseList from '@_components/PleaseList/PleaseList';
import PlusButton from '@_components/PlusButton/PlusButton';
import ROUTES from '@_constants/routes';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import PleaseLayout from '@_layouts/PleaseLayout/PleaseLayout';
import { Fragment } from 'react';
import { useNavigate } from 'react-router-dom';

export default function PleasePage() {
  const navigate = useNavigate();

  return (
    <Fragment>
      <PleaseLayout>
        <PleaseLayout.Header>우아한테크코스</PleaseLayout.Header>

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
