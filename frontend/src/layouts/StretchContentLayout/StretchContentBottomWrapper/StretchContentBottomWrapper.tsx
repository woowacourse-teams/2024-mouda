import * as S from './StretchContentBottomWrapper.style';

import { PropsWithChildren } from 'react';

export default function StretchContentBottomWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.bottomFixedStyle}>{children}</div>;
}
