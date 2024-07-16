import * as S from './HomeMain.style';

import { PropsWithChildren } from 'react';

export default function HomeMain(props: PropsWithChildren) {
  const { children } = props;
  return (
    <main css={S.mainStyle}>
      <nav css={S.navStyle}>
        <p css={S.navItemStyle(true)}>모임목록</p>
        <p css={S.navItemStyle(false)}> 나의모임</p>
      </nav>

      {children}
    </main>
  );
}
