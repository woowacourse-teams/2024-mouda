import * as S from './ChattingPreviewLayout.style';

import ChattingPreviewContainer from './ChattingPreviewContainer/ChattingPreviewContainer';
import { PropsWithChildren } from 'react';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { useTheme } from '@emotion/react';

function ChattingPreviewLayout(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.layoutStyle({ theme })}>{children}</div>;
}

ChattingPreviewLayout.Header = StickyTriSectionHeader;
ChattingPreviewLayout.ContentContainer = ChattingPreviewContainer;

export default ChattingPreviewLayout;
