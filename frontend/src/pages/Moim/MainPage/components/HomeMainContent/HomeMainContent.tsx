import MoimList from '@_components/MoimList/MoimList';
import { MainPageTab } from '@_components/MoimTabBar/MoimTabBar';
import MyMoimList from '@_components/MyMoim/MyMoim';
import MyZzimMoimList from '@_components/MyZzimMoimList/MyZzimMoimList';

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
