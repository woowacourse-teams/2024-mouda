import { PropsWithChildren } from 'react';
import * as S from './HomeHeader.style';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';

export default function HomeHeader(props: PropsWithChildren) {
  const { children } = props;

  return <header css={S.headerStyle}>{children}</header>;
}

HomeHeader.Top = StickyTriSectionHeader;
