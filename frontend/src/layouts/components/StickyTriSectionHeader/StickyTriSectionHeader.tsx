import * as S from './StickyTriSectionHeader.style';

import { PropsWithChildren } from 'react';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import { useTheme } from '@emotion/react';

function StickyTriSectionHeader(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return (
    <div css={S.Header({ theme })}>
      <TriSectionHeader>{children}</TriSectionHeader>
    </div>
  );
}

StickyTriSectionHeader.Left = TriSectionHeader.Left;
StickyTriSectionHeader.Center = TriSectionHeader.Center;
StickyTriSectionHeader.Right = TriSectionHeader.Right;

export default StickyTriSectionHeader;
