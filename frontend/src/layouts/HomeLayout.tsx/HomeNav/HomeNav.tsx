import { PropsWithChildren } from 'react';
import * as S from './HomeNav.style';

export default function HomeNav(props: PropsWithChildren) {
  const { children } = props;

  return <nav css={S.navStyle}>{children}</nav>;
}
