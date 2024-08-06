import * as S from './InformationLayout.style';

import InformationBottomWrapper from './InformationBottomWrapper/InformationBottomWrapper';
import InformationContentContainer from './InformationContentContainer/InformationLayoutContentContainer';
import { PropsWithChildren } from 'react';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';

function InformationLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

InformationLayout.Header = StickyTriSectionHeader;
InformationLayout.ContentContainer = InformationContentContainer;
InformationLayout.BottomButtonWrapper = InformationBottomWrapper;

export default InformationLayout;
