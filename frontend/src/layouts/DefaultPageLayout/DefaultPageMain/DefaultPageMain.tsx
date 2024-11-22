import * as S from './DefaultPageMain.style';

import { PropsWithChildren } from 'react';

export default function DefaultPageMain(props: PropsWithChildren) {
  const { children } = props;

  return <main css={S.mainStyle}>{children}</main>;
}
