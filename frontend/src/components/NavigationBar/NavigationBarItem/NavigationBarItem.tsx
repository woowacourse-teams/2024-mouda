import * as S from './NavigationBarItem.style';

import ChattingIcon from '@_components/Icons/ChattingIcon';
import HomeIcon from '@_components/Icons/HomeIcon';
import MyPageIcon from '@_components/Icons/MyPageIcon';
import PleaseIcon from '@_components/Icons/PleaseIcon';
import { Tab } from '@_components/NavigationBar/NavigationBar';
import { common } from '@_common/common.style';
import { useTheme } from '@emotion/react';

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
    ) : tab === '안내면진다' ? (
      // TODO: 아이콘 변경
      <PleaseIcon isActive={isActive} />
    ) : (
      <MyPageIcon isActive={isActive} />
    );

  return (
    <li
      css={S.navigationBarItem({ theme, isActive })}
      onClick={() => onClick(tab)}
    >
      {tabIcon}
      <span css={[theme.typography.c2, common.nonDrag]}>{tab}</span>
    </li>
  );
}
