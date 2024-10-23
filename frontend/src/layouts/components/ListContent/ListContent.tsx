import { PropsWithChildren } from 'react';
import * as S from './ListContent.style';

export default function ListContent(props: PropsWithChildren) {
  const { children } = props;

  return <ul css={S.ListSection}>{children}</ul>;
}
