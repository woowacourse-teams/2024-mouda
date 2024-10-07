import { PropsWithChildren } from 'react';
import * as S from './ListMain.style';

export default function ListMain(props: PropsWithChildren) {
  const { children } = props;

  return <main css={S.main}>{children}</main>;
}
