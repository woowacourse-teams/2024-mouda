import * as S from './ChatChildren.style';

import {
  formatHhmmToKoreanWithPrefix,
  formatYyyymmddToKorean,
} from '@_utils/formatters';

import { Chat } from '@_types/index';
import ChatBubble from '@_pages/Chatting/ChattingRoomPage/components/ChatBubble/ChatBubble';
import { useTheme } from '@emotion/react';

interface ChatChildrenProps {
  chat: Chat;
}
export const ChatChildren = (props: ChatChildrenProps) => {
  const { chat } = props;
  const theme = useTheme();
  if (chat.chatType === 'PLACE') {
    return (
      <ChatBubble
        isMyMessage={chat.isMyMessage}
        backgroundColor={theme.colorPalette.green[50]}
      >
        <div css={S.grey400C2({ theme })}>{'장소가 확정되었습니다!'}</div>
        <div css={theme.typography.b3}>{chat.content}</div>
      </ChatBubble>
    );
  }
  if (chat.chatType === 'DATETIME') {
    const [yyyymmdd, hhmm] = chat.content.split(' ');
    return (
      <ChatBubble
        isMyMessage={chat.isMyMessage}
        backgroundColor={theme.colorPalette.green[50]}
      >
        <div css={S.grey400C2({ theme })}>
          {'날짜와 시간이 확정되었습니다!'}
        </div>
        <div css={theme.typography.b3}>
          {formatYyyymmddToKorean(yyyymmdd, '-')}
        </div>
        <div css={theme.typography.b3}>
          {formatHhmmToKoreanWithPrefix(hhmm, ':')}
        </div>
      </ChatBubble>
    );
  }
  return (
    <ChatBubble
      isMyMessage={chat.isMyMessage}
      backgroundColor={
        chat.isMyMessage
          ? theme.colorPalette.yellow[200]
          : theme.colorPalette.orange[100]
      }
    >
      {chat.content}
    </ChatBubble>
  );
};
