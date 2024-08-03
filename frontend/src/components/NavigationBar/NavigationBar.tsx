import * as S from './NavigationBar.style';
import { useState } from 'react';
import NavigationBarItem from '@_components/NavigationBarItem/NavigationBarItem';

export type Tab = '홈' | '채팅' | '해주세요' | '마이페이지';
const tabs: Tab[] = ['홈', '채팅', '해주세요', '마이페이지'];

export default function NavigationBar() {
  const [currentTab, setCurrentTab] = useState<Tab>('홈');

  const handleTabClick = (tab: Tab) => {
    setCurrentTab(tab);
  };

  return (
    <nav css={S.navigationBarContainer}>
      <ul css={S.navigationBarList}>
        {tabs.map((tab) => (
          <NavigationBarItem
            key={tab}
            tab={tab}
            isActive={currentTab === tab}
            onClick={handleTabClick}
          />
        ))}
      </ul>
    </nav>
  );
}
