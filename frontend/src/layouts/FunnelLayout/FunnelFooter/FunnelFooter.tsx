import { PropsWithChildren } from 'react';
import * as S from './FunnelFooter.style';

export default function FunnelFooter(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.container}>{children}</div>;
}
