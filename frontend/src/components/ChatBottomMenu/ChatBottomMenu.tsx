import * as S from './ChatBottomMenu.style';

import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

export default function ChatBottomMenu(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.menu({ theme })}>{children}</div>;
}
