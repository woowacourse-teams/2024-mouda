import * as S from './InformationBottomWrapper.style';

import { PropsWithChildren } from 'react';

export default function InformationBottomWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.bottomFixedStyle}>{children}</div>;
}
