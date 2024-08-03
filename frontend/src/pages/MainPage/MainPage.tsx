import Button from '@_components/Button/Button';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import ROUTES from '@_constants/routes';
import useMoims from '@_hooks/queries/useMoims';
import { useNavigate } from 'react-router-dom';
import PlusIcon from '@_components/Icons/PlusIcon';
import MoimTabBar from '@_components/MoimTabBar/MoimTabBar';
import MoimCardList from '@_components/MoimCardList/MoimCardList';
import { Fragment, useState } from 'react';
import MyMoimListFilters from '@_components/MyMoimListFilters/MyMoimListFilters';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import NavigationBar from '@_components/NavigationBar/NavigationBar';

const tabs = ['모임목록', '나의모임', '찜한모임'];

export default function MainPage() {
  const navigate = useNavigate();
  const { moims } = useMoims();

  const [currentTab, setCurrentTab] = useState(tabs[0]);

  const handleTabClick = (tab: string) => {
    setCurrentTab(tab);
  };

  return (
    <Fragment>
      <HomeLayout>
        <HomeLayout.Header>우아한테크코스</HomeLayout.Header>

        <HomeLayout.Nav>
          <MoimTabBar
            tabs={tabs}
            currentTab={currentTab}
            onTabClick={handleTabClick}
          />
        </HomeLayout.Nav>

        <HomeLayout.Main>
          {currentTab === '나의모임' && <MyMoimListFilters />}
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

      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
