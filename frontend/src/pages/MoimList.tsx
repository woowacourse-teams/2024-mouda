import Button from '../components/Button/Button';
import ENDPOINTS from '../constants/endpoints';
import HomeLayout from '../layouts/HomeLayout.tsx/HomeLayout';
import MoimCardList from '../components/MoimCardList/MoimCardList';
import Plus from '../common/assets/tabler_plus.svg';
import useMoims from '../queries/useMoims';
import { useNavigate } from 'react-router-dom';

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
        <Button
          shape="circle"
          onClick={() => navigate(ENDPOINTS.addMoim)}
          disabled={false}
        >
          <img src={Plus} />
        </Button>
      </HomeLayout.HomeFixedButtonWrapper>
    </HomeLayout>
  );
}
