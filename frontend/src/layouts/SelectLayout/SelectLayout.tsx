import * as S from './SelectLayout.style';

import { PropsWithChildren } from 'react';
import SelectBottomWrapper from './SelectBottomWrapper/SelectBottomWrapper';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import ListContent from '@_layouts/components/ListContent/ListContent';

function SelectLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

SelectLayout.Header = StickyTriSectionHeader;
SelectLayout.ContentContainer = ListContent;
SelectLayout.BottomButtonWrapper = SelectBottomWrapper;

export default SelectLayout;
