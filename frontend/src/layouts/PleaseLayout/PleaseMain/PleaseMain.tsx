import * as S from './PleaseMain.style';

import { PropsWithChildren } from 'react';

export default function PleaseMain(props: PropsWithChildren) {
  const { children } = props;

  return <main css={S.mainStyle}>{children}</main>;
}
