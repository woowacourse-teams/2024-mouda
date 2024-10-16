import { useEffect, useRef } from 'react';

import Chat from '@_pages/Chatting/ChattingRoomPage/components/Chat/Chat';
import { ChatChildren } from '@_pages/Chatting/ChattingRoomPage/components/ChatBubble/ChatChildren/ChatChildren';
import { Chat as ChatType } from '@_types/index';
import ChattingRoomSeparator from '../ChattingRoomSeparator/ChattingRoomSeparator';
import { formatYyyymmddToKorean } from '@_utils/formatters';
import { list } from './ChatList.style';
import { useTheme } from '@emotion/react';

interface ChatListProps {
  chats: ChatType[];
}

export default function ChatList(props: ChatListProps) {
  const { chats } = props;
  const endRef = useRef<HTMLDivElement | null>(null);

  const theme = useTheme();

  useEffect(() => {
    endRef.current?.scrollIntoView();
  }, [chats]);

  return (
    <div css={list({ theme })}>
      {chats.map((chat, index) => {
        if (chats[index - 1]?.date !== chat.date) {
          return (
            <div key={chat.chatId}>
              <ChattingRoomSeparator
                string={formatYyyymmddToKorean(chat.date, '-', true)}
              />
              <Chat chat={chat}>
                <ChatChildren chat={chat} />
              </Chat>
            </div>
          );
        }
        return (
          <Chat key={chat.chatId} chat={chat}>
            <ChatChildren chat={chat} />
          </Chat>
        );
      })}
      <div ref={endRef} />
    </div>
  );
}
