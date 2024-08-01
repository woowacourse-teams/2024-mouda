import * as S from './ChattingRoomHeader.style';

import { PropsWithChildren } from 'react';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import { useTheme } from '@emotion/react';

function ChattingRoomHeader(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return (
    <div css={S.Header({ theme })}>
      <TriSectionHeader>{children}</TriSectionHeader>
    </div>
  );
}

ChattingRoomHeader.Left = TriSectionHeader.Left;
ChattingRoomHeader.Center = TriSectionHeader.Center;
ChattingRoomHeader.Right = TriSectionHeader.Right;

export default ChattingRoomHeader;
