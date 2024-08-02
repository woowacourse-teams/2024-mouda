import { useTheme } from '@emotion/react';
import * as S from './MoimTabBar.style';

interface MoimTabBarProps {
  tabs: string[];
  currentTab: string;
  onTabClick: (tab: string) => void;
}

export default function MoimTabBar(props: MoimTabBarProps) {
  const { tabs, currentTab, onTabClick } = props;

  const theme = useTheme();

  return (
    <nav css={S.tabStyle({ theme })}>
      {tabs.map((tab, index) => (
        <p
          key={index}
          css={S.tabItemStyle({ theme, isTurnedOn: currentTab === tab })}
          onClick={() => onTabClick(tab)}
        >
          {tab}
        </p>
      ))}
    </nav>
  );
}
