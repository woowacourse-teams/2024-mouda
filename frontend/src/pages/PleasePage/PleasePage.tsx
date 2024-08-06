import Button from '@_components/Button/Button';
import PlusIcon from '@_components/Icons/PlusIcon';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import PleaseList from '@_components/PleaseList/PleaseList';
import ROUTES from '@_constants/routes';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import { Fragment } from 'react';
import { useNavigate } from 'react-router-dom';

export default function PleasePage() {
  const navigate = useNavigate();

  return (
    <Fragment>
      <HomeLayout>
        <HomeLayout.Header>우아한테크코스</HomeLayout.Header>

        <HomeLayout.Main>
          <PleaseList />
        </HomeLayout.Main>

        <HomeLayout.HomeFixedButtonWrapper>
          <Button
            shape="circle"
            onClick={() => navigate(ROUTES.please)}
            disabled={false}
          >
            <PlusIcon />
          </Button>
        </HomeLayout.HomeFixedButtonWrapper>
      </HomeLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
