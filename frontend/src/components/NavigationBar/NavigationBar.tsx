import * as S from './NavigationBar.style';

import { useLocation, useNavigate } from 'react-router-dom';

import GET_ROUTES from '@_common/getRoutes';
import NavigationBarItem from '@_components/NavigationBar/NavigationBarItem/NavigationBarItem';
import { useState } from 'react';
import { useTheme } from '@emotion/react';

export type Tab = '홈' | '채팅' | '안내면진다' | '마이페이지';

export default function NavigationBar() {
  const theme = useTheme();
  const navigate = useNavigate();
  const location = useLocation();

  const tabRoutes: Record<Tab, string> = {
    홈: GET_ROUTES.nowDarakbang.main(),
    채팅: GET_ROUTES.nowDarakbang.chat(),
    안내면진다: GET_ROUTES.nowDarakbang.bet(),
    마이페이지: GET_ROUTES.nowDarakbang.myPage(),
  };

  const [currentTab, setCurrentTab] = useState<Tab>(
    Object.keys(tabRoutes).find(
      (key) => tabRoutes[key as Tab] === location.pathname,
    ) as Tab,
  );

  const handleTabClick = (tab: Tab) => {
    setCurrentTab(tab);
    navigate(tabRoutes[tab]);
  };

  return (
    <nav id="bottom-nav" css={S.navigationBarContainer({ theme })}>
      <ul css={[S.navigationBarList]} role="menu">
        {Object.keys(tabRoutes).map((tab) => (
          <NavigationBarItem
            key={tab}
            tab={tab as Tab}
            isActive={currentTab === tab}
            onClick={handleTabClick}
          />
        ))}
      </ul>
    </nav>
  );
}
