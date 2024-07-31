import * as S from './CompleteLayout.style';

import CompleteBottomWrapper from './CompleteBottomWrapper/CompleteBottomWrapper';
import CompleteContentContainer from './CompleteContentContainer/CompleteContentContainer';
import { PropsWithChildren } from 'react';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';

function CompleteLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

CompleteLayout.Header = TriSectionHeader;
CompleteLayout.ContentContainer = CompleteContentContainer;
CompleteLayout.BottomButtonWrapper = CompleteBottomWrapper;

export default CompleteLayout;
