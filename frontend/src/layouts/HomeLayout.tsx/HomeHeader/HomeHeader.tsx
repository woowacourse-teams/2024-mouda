import { PropsWithChildren } from 'react';
import * as S from './HomeHeader.style';

export default function HomeHeader(props: PropsWithChildren) {
  const { children } = props;

  return <header css={S.headerStyle}>{children}</header>;
}
