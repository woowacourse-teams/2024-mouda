import HomeLayout from '../layouts/HomeLayout.tsx/HomeLayout';
import { meetings } from '../layouts/HomeLayout.tsx/mockData';
import MoimCardList from '../components/MoimCardList/MoimCardList';
import Button from '../components/Button/Button';

export default function MoimListPage() {
  return (
    <HomeLayout>
      <HomeLayout.Header>우아한테크코스</HomeLayout.Header>
      <HomeLayout.Main>
        <MoimCardList data={meetings} />
      </HomeLayout.Main>
      <HomeLayout.HomeFixedButtonWrapper>
        <Button shape="circle">+</Button>
      </HomeLayout.HomeFixedButtonWrapper>
    </HomeLayout>
  );
}
