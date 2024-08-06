import Button from '@_components/Button/Button';
import PlusIcon from '@_components/Icons/PlusIcon';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import PleaseList from '@_components/PleaseList/PleaseList';
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
          <Button
            shape="circle"
            onClick={() => navigate(ROUTES.please)}
            disabled={false}
          >
            <PlusIcon />
          </Button>
        </PleaseLayout.HomeFixedButtonWrapper>
      </PleaseLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
