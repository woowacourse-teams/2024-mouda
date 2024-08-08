import ChattingIcon from '@_components/Icons/ChattingIcon';
import HomeIcon from '@_components/Icons/HomeIcon';
import MyPageIcon from '@_components/Icons/MyPageIcon';
import PleaseIcon from '@_components/Icons/PleaseIcon';
import { useTheme } from '@emotion/react';
import * as S from './NavigationBarItem.style';
import { Tab } from '@_components/NavigationBar/NavigationBar';

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
    ) : tab === '해주세요' ? (
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
      <span css={theme.typography.c2}>{tab}</span>
    </li>
  );
}
