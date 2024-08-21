import * as S from './SelectBottomWrapper.style';

import { PropsWithChildren } from 'react';

export default function SelectBottomWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.bottomFixedStyle}>{children}</div>;
}
