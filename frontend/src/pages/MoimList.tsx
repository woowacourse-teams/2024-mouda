import HomeLayout from '../layouts/HomeLayout.tsx/HomeLayout';
import MoimCardList from '../components/MoimCardList/MoimCardList';
import Button from '../components/Button/Button';
import Plus from '../common/assets/tabler_plus.svg';
import { useNavigate } from 'react-router-dom';
import ENDPOINTS from '../constants/endpoints';
import useMoims from '../queries/useMoims';

export default function MoimListPage() {
  const navigate = useNavigate();
  const { moims } = useMoims();
  return (
    <HomeLayout>
      <HomeLayout.Header>우아한테크코스</HomeLayout.Header>
      <HomeLayout.Main>
        {moims && <MoimCardList data={moims} />}
      </HomeLayout.Main>

      <HomeLayout.HomeFixedButtonWrapper>
        <Button shape="circle" onClick={() => navigate(ENDPOINTS.addMoim)}>
          <img src={Plus} />
        </Button>
      </HomeLayout.HomeFixedButtonWrapper>
    </HomeLayout>
  );
}
