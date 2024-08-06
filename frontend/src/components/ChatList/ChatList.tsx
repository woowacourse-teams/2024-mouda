import Chat from '@_components/Chat/Chat';
import { Chat as ChatType } from '@_types/index';
import { list } from './ChatList.style';
import { useEffect } from 'react';
import { useTheme } from '@emotion/react';

interface ChatListProps {
  chats: ChatType[];
}

export default function ChatList(props: ChatListProps) {
  const { chats } = props;

  const theme = useTheme();

  useEffect(() => {
    window.scrollTo(0, document.body.scrollHeight);
  }, [chats]);

  return (
    <div css={list({ theme })}>
      {chats.map((chat) => (
        <Chat key={chat.chatId} chat={chat} />
      ))}
    </div>
  );
}
