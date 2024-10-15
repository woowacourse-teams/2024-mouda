import { PropsWithChildren } from 'react';
import * as S from './DoubleTriHeader.style';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';

export default function DoubleTriHeader(props: PropsWithChildren) {
  const { children } = props;

  return <header css={S.headerStyle}>{children}</header>;
}

DoubleTriHeader.Top = StickyTriSectionHeader;
