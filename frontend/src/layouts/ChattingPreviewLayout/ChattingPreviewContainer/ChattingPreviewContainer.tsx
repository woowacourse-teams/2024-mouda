import * as S from './ChattingPreviewContainer.style';

import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

export default function ChattingPreviewContainer(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.Container({ theme })}>{children}</div>;
}
