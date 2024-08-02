import Button from '@_components/Button/Button';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import ROUTES from '@_constants/routes';
import { useNavigate } from 'react-router-dom';
import PlusIcon from '@_components/Icons/PlusIcon';
import MoimTabBar, { MainPageTab } from '@_components/MoimTabBar/MoimTabBar';
import { useState } from 'react';
import HomeMainContent from '@_components/HomeMainContent/HomeMainContent';

export default function MainPage() {
  const navigate = useNavigate();

  const [currentTab, setCurrentTab] = useState<MainPageTab>('모임목록');

  const handleTabClick = (tab: MainPageTab) => {
    setCurrentTab(tab);
  };

  return (
    <HomeLayout>
      <HomeLayout.Header>우아한테크코스</HomeLayout.Header>

      <HomeLayout.Nav>
        <MoimTabBar currentTab={currentTab} onTabClick={handleTabClick} />
      </HomeLayout.Nav>

      <HomeLayout.Main>
        <HomeMainContent currentTab={currentTab} />
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
