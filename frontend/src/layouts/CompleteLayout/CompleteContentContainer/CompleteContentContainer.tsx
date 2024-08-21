import * as S from './CompleteContentContainer.style';

import { PropsWithChildren } from 'react';

export default function CompleteContentContainer(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.contentStyle}>{children}</div>;
}
