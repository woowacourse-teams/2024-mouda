import * as S from './StretchContentLayout.style';

import { PropsWithChildren } from 'react';
import StretchContentBottomWrapper from './StretchContentBottomWrapper/StretchContentBottomWrapper';
import StretchContentContainer from './StretchContentContainer/StretchContentContainer';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';

function StretchContentLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

StretchContentLayout.Header = TriSectionHeader;
StretchContentLayout.ContentContainer = StretchContentContainer;
StretchContentLayout.BottomButtonWrapper = StretchContentBottomWrapper;

export default StretchContentLayout;
