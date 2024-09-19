import { MainPageTab } from '@_pages/Moim/MainPage/components/MoimTabBar/MoimTabBar';
import MoimList from './MoimList/MoimList';
import MyMoimList from './MyMoim/MyMoim';
import MyZzimMoimList from './MyZzimMoimList/MyZzimMoimList';

interface MoimMainContentProps {
  currentTab: MainPageTab;
}

export default function HomeMainContent(props: MoimMainContentProps) {
  const { currentTab } = props;

  if (currentTab === '모임목록') {
    return <MoimList />;
  }
  if (currentTab === '나의모임') {
    return <MyMoimList />;
  }
  if (currentTab === '찜한모임') {
    return <MyZzimMoimList />;
  }
}
