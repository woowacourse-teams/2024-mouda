import { PropsWithChildren } from 'react';
import * as S from './FunnelMain.style';

export default function FunnelMain(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.container}>{children}</div>;
}
