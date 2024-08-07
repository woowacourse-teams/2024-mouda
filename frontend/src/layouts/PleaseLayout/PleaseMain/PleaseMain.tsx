import { useTheme } from '@emotion/react';
import * as S from './PleaseMain.style';

import { PropsWithChildren } from 'react';

export default function PleaseMain(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <main css={S.mainStyle({ theme })}>{children}</main>;
}
