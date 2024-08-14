import * as S from './SelectBar.style';

import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

export default function SelectBar(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.selectBar({ theme })}>{children}</div>;
}
