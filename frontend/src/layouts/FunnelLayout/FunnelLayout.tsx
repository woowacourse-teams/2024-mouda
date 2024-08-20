import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { PropsWithChildren } from 'react';
import * as S from './FunnelLayout.style';
import FunnelMain from './FunnelMain/FunnelMain';
import FunnelFooter from './FunnelFooter/FunnelFooter';

function FunnelLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.container}>{children}</div>;
}

FunnelLayout.Header = StickyTriSectionHeader;
FunnelLayout.Main = FunnelMain;
FunnelLayout.Footer = FunnelFooter;

export default FunnelLayout;
