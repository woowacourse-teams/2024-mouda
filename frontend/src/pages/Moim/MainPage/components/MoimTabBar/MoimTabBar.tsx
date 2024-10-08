import * as S from './MoimTabBar.style';

import { common } from '@_common/common.style';
import { useTheme } from '@emotion/react';

export type MainPageTab = '모임목록' | '나의모임' | '찜한모임';

const tabs: MainPageTab[] = ['모임목록', '나의모임', '찜한모임'];

interface MoimTabBarProps {
  currentTab: MainPageTab;
  onTabClick: (tab: MainPageTab) => void;
}

export default function MoimTabBar(props: MoimTabBarProps) {
  const { currentTab, onTabClick } = props;

  const theme = useTheme();

  return (
    <nav css={S.tabStyle}>
      {tabs.map((tab, index) => (
        <p
          key={index}
          css={[
            S.tabItemStyle({ theme, isTurnedOn: currentTab === tab }),
            common.nonDrag,
          ]}
          onClick={() => onTabClick(tab)}
        >
          {tab}
        </p>
      ))}
    </nav>
  );
}
