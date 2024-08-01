import * as S from './ChattingPreviewLayout.style';

import { PropsWithChildren } from 'react';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { useTheme } from '@emotion/react';

function ChattingPreview(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.layoutStyle({ theme })}>{children}</div>;
}

ChattingPreview.Header = StickyTriSectionHeader;
ChattingPreview.ContentContainer = ChattingPreview;

export default ChattingPreview;
