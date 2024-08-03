import * as S from './NavigationBar.style';
import { useState } from 'react';
import NavigationBarItem from '@_components/NavigationBarItem/NavigationBarItem';
import { useTheme } from '@emotion/react';
import { useLocation, useNavigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';

export type Tab = '홈' | '채팅' | '해주세요' | '마이페이지';

const tabRoutes: Record<Tab, string> = {
  홈: ROUTES.main,
  채팅: ROUTES.chat,
  해주세요: ROUTES.please,
  마이페이지: ROUTES.myPage,
};

export default function NavigationBar() {
  const theme = useTheme();
  const navigate = useNavigate();
  const location = useLocation();

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
    <nav css={S.navigationBarContainer({ theme })}>
      <ul css={S.navigationBarList}>
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
