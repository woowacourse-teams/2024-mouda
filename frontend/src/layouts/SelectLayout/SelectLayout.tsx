import * as S from './SelectLayout.style';

import { PropsWithChildren } from 'react';
import SelectBottomWrapper from './SelectBottomWrapper/SelectBottomWrapper';
import SelectContentContainer from './SelectContentContainer/SelectContentContainer';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';

function SelectLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

SelectLayout.Header = StickyTriSectionHeader;
SelectLayout.ContentContainer = SelectContentContainer;
SelectLayout.BottomButtonWrapper = SelectBottomWrapper;

export default SelectLayout;
