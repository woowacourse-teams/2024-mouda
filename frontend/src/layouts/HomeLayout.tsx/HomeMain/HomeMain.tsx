import { useTheme } from '@emotion/react';
import * as S from './HomeMain.style';

import { PropsWithChildren } from 'react';

export default function HomeMain(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <main css={S.mainStyle({ theme })}>{children}</main>;
}
