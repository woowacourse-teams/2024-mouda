import { Fragment, useState } from 'react';
import MoimTabBar, { MainPageTab } from '@_components/MoimTabBar/MoimTabBar';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import HomeMainContent from '@_components/HomeMainContent/HomeMainContent';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import ROUTES from '@_constants/routes';
import { useNavigate } from 'react-router-dom';
import PlusButton from '@_components/PlusButton/PlusButton';
import Notification from '@_common/assets/notification.svg';
import * as S from './MainPage.style';

export default function MainPage() {
  const navigate = useNavigate();

  const [currentTab, setCurrentTab] = useState<MainPageTab>('모임목록');

  const handleTabClick = (tab: MainPageTab) => {
    setCurrentTab(tab);
  };

  const handleNotification = () => {
    navigate(ROUTES.notification);
  };

  return (
    <Fragment>
      <HomeLayout>
        <HomeLayout.Header>
          <HomeLayout.Header.Top>
            <HomeLayout.Header.Top.Left>
              우아한테크코스
            </HomeLayout.Header.Top.Left>
            <HomeLayout.Header.Top.Right>
              <button css={S.headerButton} onClick={handleNotification}>
                <Notification />
              </button>
            </HomeLayout.Header.Top.Right>
          </HomeLayout.Header.Top>
          <MoimTabBar currentTab={currentTab} onTabClick={handleTabClick} />
        </HomeLayout.Header>

        <HomeLayout.Main>
          <HomeMainContent currentTab={currentTab} />
        </HomeLayout.Main>

        <HomeLayout.HomeFixedButtonWrapper>
          <PlusButton onClick={() => navigate(ROUTES.addMoim)} />
        </HomeLayout.HomeFixedButtonWrapper>
      </HomeLayout>

      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
