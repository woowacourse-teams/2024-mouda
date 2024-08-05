import { useEffect, useRef } from 'react';

import Chat from '@_components/Chat/Chat';
import { Chat as ChatType } from '@_types/index';
import { list } from './ChatList.style';
import { useTheme } from '@emotion/react';

interface ChatListProps {
  chats: ChatType[];
}

export default function ChatList(props: ChatListProps) {
  const { chats } = props;
  const messageEndRef = useRef<HTMLDivElement | null>(null);

  const theme = useTheme();

  useEffect(() => {
    messageEndRef.current?.scrollIntoView();
  }, [chats]);

  return (
    <div css={list({ theme })}>
      {chats.map((chat) => (
        <Chat key={chat.chatId} chat={chat} />
      ))}
      <div ref={messageEndRef} tabIndex={1}></div>
    </div>
  );
}
