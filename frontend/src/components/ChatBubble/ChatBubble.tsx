import * as S from './ChatBubble.style';

import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

interface ChatBubbleProps extends PropsWithChildren {
  isMyMessage: boolean;
  backgroundColor?: string;
}

export default function ChatBubble(props: ChatBubbleProps) {
  const { isMyMessage, backgroundColor, children } = props;
  const theme = useTheme();

  return (
    <div css={S.chatBubble({ theme, backgroundColor, isMyMessage })}>
      {children}
    </div>
  );
}
