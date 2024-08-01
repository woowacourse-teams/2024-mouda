import * as S from './ChattingPreviewLayout.style';

import { PropsWithChildren } from 'react';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import { useTheme } from '@emotion/react';

function ChattingPreview(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return <div css={S.layoutStyle({ theme })}>{children}</div>;
}

ChattingPreview.Header = TriSectionHeader;
ChattingPreview.ContentContainer = ChattingPreview;

export default ChattingPreview;
