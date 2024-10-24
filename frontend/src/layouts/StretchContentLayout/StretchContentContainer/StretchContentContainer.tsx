import * as S from './StretchContentContainer.style';

import { PropsWithChildren } from 'react';

export default function StretchContentContainer(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.contentStyle}>{children}</div>;
}
