import * as S from './ChattingRoomLayout.style';

import ChattingRoomFooter from './ChattingRoomFooter/ChattingRoomFooter';
import ChattingRoomHeader from './ChattingRoomHeader/ChattingRoomHeader';
import { PropsWithChildren } from 'react';

function ChattingRoomLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

ChattingRoomLayout.Header = ChattingRoomHeader;
ChattingRoomLayout.Footer = ChattingRoomFooter;

export default ChattingRoomLayout;
