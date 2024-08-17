import * as S from './SelectContentContainer.style';

import { PropsWithChildren } from 'react';

export default function SelectContentContainer(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.contentStyle}>{children}</div>;
}
