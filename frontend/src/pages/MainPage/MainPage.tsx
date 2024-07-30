import Button from '@_components/Button/Button';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import ROUTES from '@_constants/routes';
import useMoims from '@_hooks/queries/useMoims';
import { useNavigate } from 'react-router-dom';
import PlusIcon from '@_components/PlusIcon/PlusIcon';
import MoimTabBar from '@_components/Home/MoimTabBar/MoimTabBar';
import MoimCardList from '@_components/Home/MoimCardList/MoimCardList';

export default function MainPage() {
  const navigate = useNavigate();
  const { moims } = useMoims();

  return (
    <HomeLayout>
      <HomeLayout.Header>우아한테크코스</HomeLayout.Header>

      <HomeLayout.Nav>
        <MoimTabBar />
      </HomeLayout.Nav>

      <HomeLayout.Main>
        {moims && <MoimCardList moimInfos={moims} />}
      </HomeLayout.Main>

      <HomeLayout.HomeFixedButtonWrapper>
        <Button
          shape="circle"
          onClick={() => navigate(ROUTES.addMoim)}
          disabled={false}
        >
          <PlusIcon />
        </Button>
      </HomeLayout.HomeFixedButtonWrapper>
    </HomeLayout>
  );
}
