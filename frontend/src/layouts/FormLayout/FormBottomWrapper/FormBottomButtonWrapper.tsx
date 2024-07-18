import * as S from './FormBottomWrapper.style';

import { PropsWithChildren } from 'react';

export default function FormBottomWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.bottomFixedStyle}>{children}</div>;
}
