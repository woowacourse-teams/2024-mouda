import { PropsWithChildren } from 'react';
import * as S from './LoginFooter.style';

export default function LoginFooter(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.footerStyle}>{children}</div>;
}
