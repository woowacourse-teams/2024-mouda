import * as S from './InformationLayout.style';

import InformationBottomWrapper from './InformationBottomWrapper/InformationBottomWrapper';
import InformationContentContainer from './InformationContentContainer/InformationLayoutContentContainer';
import { PropsWithChildren } from 'react';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';

function InformationLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

InformationLayout.Header = TriSectionHeader;
InformationLayout.ContentContainer = InformationContentContainer;
InformationLayout.BottomButtonWrapper = InformationBottomWrapper;

export default InformationLayout;
