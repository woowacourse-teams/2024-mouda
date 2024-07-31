import * as S from './CompleteBottomWrapper.style';

import { PropsWithChildren } from 'react';

export default function CompleteBottomWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.bottomFixedStyle}>{children}</div>;
}
