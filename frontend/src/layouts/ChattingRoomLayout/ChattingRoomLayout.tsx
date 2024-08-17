import * as S from './ChattingRoomLayout.style';

import ChattingRoomFooter from './ChattingRoomFooter/ChattingRoomFooter';
import { PropsWithChildren } from 'react';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { useTheme } from '@emotion/react';

function ChattingRoomLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

function Header(props: PropsWithChildren) {
  const { children } = props;

  return <StickyTriSectionHeader>{children}</StickyTriSectionHeader>;
}

Header.Left = StickyTriSectionHeader.Left;
Header.Right = StickyTriSectionHeader.Right;
Header.Center = function Center(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();
  return (
    <StickyTriSectionHeader.Center>
      <div css={S.headerCenter({ theme })}>{children}</div>
    </StickyTriSectionHeader.Center>
  );
};

ChattingRoomLayout.Header = Header;
ChattingRoomLayout.Footer = ChattingRoomFooter;

export default ChattingRoomLayout;
