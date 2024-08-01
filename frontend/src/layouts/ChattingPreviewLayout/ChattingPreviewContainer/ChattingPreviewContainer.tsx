import * as S from './ChattingPreviewContainer.style';

import { PropsWithChildren } from 'react';

export default function ChattingPreviewContainer(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.contentStyle}>{children}</div>;
}
