import ChatMessage, {
  ChatMessageProps,
} from '@_components/ChatMessage/ChatMessage';

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
        <ChatMessage key={chat.message + chat.time} {...chat} />
      ))}
    </div>
  );
}
