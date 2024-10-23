import * as S from './NavigationBarItem.style';

import ChattingIcon from '@_components/Icons/ChattingIcon';
import HomeIcon from '@_components/Icons/HomeIcon';
import MyPageIcon from '@_components/Icons/MyPageIcon';
import { Tab } from '@_components/NavigationBar/NavigationBar';
import { common } from '@_common/common.style';
import { useTheme } from '@emotion/react';
import ScissorsIcon from '@_components/Icons/ScissorsIcon';

interface NavigationBarItemProps {
  tab: Tab;
  isActive: boolean;
  onClick: (tab: Tab) => void;
}

export default function NavigationBarItem(props: NavigationBarItemProps) {
  const { tab, isActive, onClick } = props;

  const theme = useTheme();

  const tabIcon =
    tab === '홈' ? (
      <HomeIcon isActive={isActive} />
    ) : tab === '채팅' ? (
      <ChattingIcon isActive={isActive} />
    ) : tab === '룰렛' ? (
      <ScissorsIcon isActive={isActive} />
    ) : (
      <MyPageIcon isActive={isActive} />
    );

  return (
    <li
      role="menuitem"
      aria-label={tab}
      css={S.navigationBarItem({ theme, isActive })}
      onClick={() => onClick(tab)}
      tabIndex={0}
    >
      {tabIcon}
      <span css={[theme.typography.c2, common.nonDrag]}>{tab}</span>
    </li>
  );
}
