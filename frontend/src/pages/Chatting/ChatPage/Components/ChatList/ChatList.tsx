import { useEffect, useRef } from 'react';

import Chat from '@_pages/Chatting/ChatPage/Components/Chat/Chat';
import { ChatChildren } from '@_pages/Chatting/ChatPage/Components/ChatBubble/ChatChildren/ChatChildren';
import { Chat as ChatType } from '@_types/index';
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
      {chats.map((chat) => {
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
