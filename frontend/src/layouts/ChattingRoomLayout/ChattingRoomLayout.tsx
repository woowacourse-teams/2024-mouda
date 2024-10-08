import * as S from './ChattingRoomLayout.style';

import ChattingRoomFooter from './ChattingRoomFooter/ChattingRoomFooter';
import { PropsWithChildren } from 'react';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';

function ChattingRoomLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

function Header(props: PropsWithChildren) {
  const { children } = props;

  return <StickyTriSectionHeader>{children}</StickyTriSectionHeader>;
}

function HeaderBottom(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.headerBottom}>{children}</div>;
}

Header.Left = StickyTriSectionHeader.Left;
Header.Right = StickyTriSectionHeader.Right;
Header.Center = StickyTriSectionHeader.Center;

ChattingRoomLayout.Header = Header;
ChattingRoomLayout.Footer = ChattingRoomFooter;
ChattingRoomLayout.HeaderBottom = HeaderBottom;

export default ChattingRoomLayout;
