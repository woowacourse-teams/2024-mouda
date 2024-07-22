import * as S from './CompleteLayout.style';

import CompleteBottomWrapper from './CompleteBottomWrapper/CompleteBottomWrapper';
import CompleteContentContainer from './CompleteContentContainer/CompleteContentContainer';
import { PropsWithChildren } from 'react';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';

function InformationLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

InformationLayout.Header = TriSectionHeader;
InformationLayout.ContentContainer = CompleteContentContainer;
InformationLayout.BottomButtonWrapper = CompleteBottomWrapper;

export default InformationLayout;
