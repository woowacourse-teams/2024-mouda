import { Fragment, useState } from 'react';
import MoimTabBar, { MainPageTab } from '@_components/MoimTabBar/MoimTabBar';
import Button from '@_components/Button/Button';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import HomeMainContent from '@_components/HomeMainContent/HomeMainContent';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import PlusIcon from '@_components/Icons/PlusIcon';
import ROUTES from '@_constants/routes';
import { useNavigate } from 'react-router-dom';
import HomeHeaderContent from '@_components/HomeHeaderContent/HomHeaderContent';

export default function MainPage() {
  const navigate = useNavigate();

  const [currentTab, setCurrentTab] = useState<MainPageTab>('모임목록');

  const handleTabClick = (tab: MainPageTab) => {
    setCurrentTab(tab);
  };

  return (
    <Fragment>
      <HomeLayout>
        <HomeLayout.Header>
          <HomeHeaderContent>우아한테크코스</HomeHeaderContent>
          <MoimTabBar currentTab={currentTab} onTabClick={handleTabClick} />
        </HomeLayout.Header>

        <HomeLayout.Main>
          <HomeMainContent currentTab={currentTab} />
        </HomeLayout.Main>

        <HomeLayout.HomeFixedButtonWrapper>
          <Button
            shape="circle"
            onClick={() => navigate(ROUTES.addMoim)}
            disabled={false}
            reversePrimary
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
