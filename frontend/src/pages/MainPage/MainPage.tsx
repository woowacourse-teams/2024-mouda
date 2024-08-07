import Button from '@_components/Button/Button';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import ROUTES from '@_constants/routes';
import { useNavigate } from 'react-router-dom';
import PlusIcon from '@_components/Icons/PlusIcon';
import { Fragment, useState } from 'react';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import NavigationBar from '@_components/NavigationBar/NavigationBar';

import MoimTabBar, { MainPageTab } from '@_components/MoimTabBar/MoimTabBar';
import HomeMainContent from '@_components/HomeMainContent/HomeMainContent';
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
