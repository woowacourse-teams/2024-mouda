import * as S from './ListPageMain.style';

import { PropsWithChildren } from 'react';

export default function ListPageMain(props: PropsWithChildren) {
  const { children } = props;

  return <main css={S.mainStyle}>{children}</main>;
}
