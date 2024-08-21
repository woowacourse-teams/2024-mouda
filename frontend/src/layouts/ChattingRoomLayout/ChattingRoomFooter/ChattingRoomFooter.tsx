import * as S from './ChattingRoomFooter.style';

import { PropsWithChildren } from 'react';

function ChattingRoomFooter(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.footer}>{children}</div>;
}

export default ChattingRoomFooter;
