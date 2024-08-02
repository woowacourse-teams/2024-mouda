import * as S from './HomeMain.style';

import { PropsWithChildren } from 'react';

export default function HomeMain(props: PropsWithChildren) {
  const { children } = props;

  return <main css={S.mainStyle}>{children}</main>;
}
