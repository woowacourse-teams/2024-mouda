import { useTheme } from '@emotion/react';
import * as S from './CompleteBottomWrapper.style';

import { PropsWithChildren } from 'react';

export default function CompleteBottomWrapper(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.bottomFixedStyle({ theme })}>{children}</div>;
}
