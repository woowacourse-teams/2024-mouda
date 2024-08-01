import { PropsWithChildren } from 'react';
import * as S from './LoginMain.style';

export default function LoginMain(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.mainStyle}>{children}</div>;
}
