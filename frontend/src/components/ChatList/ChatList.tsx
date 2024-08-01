import Chat, { ChatMessageProps } from '@_components/Chat/Chat';

import { list } from './ChatList.style';
import { useTheme } from '@emotion/react';

interface ChatListProps {
  chats: ChatMessageProps[];
}

export default function ChatList(props: ChatListProps) {
  const { chats } = props;
  const theme = useTheme();

  return (
    <div css={list({ theme })}>
      {chats.map((chat) => (
        <Chat key={chat.message + chat.time} {...chat} />
      ))}
    </div>
  );
}
