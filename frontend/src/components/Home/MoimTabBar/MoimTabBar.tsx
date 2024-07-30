import { useTheme } from '@emotion/react';
import * as S from './MoimTabBar.style';

const tabs = ['모임목록', '나의모임', '찜한모임'];

export default function MoimTabBar() {
  const theme = useTheme();

  return (
    <nav css={S.tabStyle({ theme })}>
      {tabs.map((tab, index) => (
        <p key={index} css={S.tabItemStyle({ theme, isTurnedOn: index === 0 })}>
          {tab}
        </p>
      ))}
    </nav>
  );
}
