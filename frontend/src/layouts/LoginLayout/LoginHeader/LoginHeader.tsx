import { PropsWithChildren } from 'react';
import * as S from './LoginHeader.style';

export default function LoginHeader(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.headerStyle}>{children}</div>;
}
