import Button from '@_components/Button/Button';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import MoimCardList from '@_components/MoimCardList/MoimCardList';
import Plus from '@_common/assets/tabler_plus.svg';
import ROUTES from '@_constants/routes';
import useMoims from '@_hooks/queries/useMoims';
import { useNavigate } from 'react-router-dom';

export default function MainPage() {
  const navigate = useNavigate();
  const { moims } = useMoims();

  return (
    <HomeLayout>
      <HomeLayout.Header>우아한테크코스</HomeLayout.Header>
      <HomeLayout.Main>
        {moims && <MoimCardList moimInfos={moims} />}
      </HomeLayout.Main>

      <HomeLayout.HomeFixedButtonWrapper>
        <Button
          shape="circle"
          onClick={() => navigate(ROUTES.addMoim)}
          disabled={false}
        >
          <Plus />
        </Button>
      </HomeLayout.HomeFixedButtonWrapper>
    </HomeLayout>
  );
}
