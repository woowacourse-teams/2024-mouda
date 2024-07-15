import { PropsWithChildren } from 'react';
import * as S from './HomeMain.style';

export default function HomeMain(props: PropsWithChildren) {
  const { children } = props;
  return (
    <main css={S.mainStyle}>
      <nav css={S.navStyle}>
        <p css={S.navItemStyle}>모임목록</p>
        <p css={S.navItemStyle}> 나의모임</p>
      </nav>

      {children}
    </main>
  );
}
