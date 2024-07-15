import { PropsWithChildren } from 'react';
import * as S from './HomeHeader.style';

export default function HomeHeader(props: PropsWithChildren) {
  const { children } = props;

  return (
    <header css={S.headerStyle}>
      <h1 css={S.logoStyle}>{children}</h1>
    </header>
  );
}
