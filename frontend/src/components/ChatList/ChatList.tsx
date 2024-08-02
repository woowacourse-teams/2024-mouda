import Chat, { ChatMessageProps } from '@_components/Chat/Chat';
import { useEffect, useRef, useState } from 'react';

import { list } from './ChatList.style';
import { useTheme } from '@emotion/react';

interface ChatListProps {
  chats: ChatMessageProps[];
}

export default function ChatList(props: ChatListProps) {
  const { chats } = props;
  const [lastChats, setLastChats] = useState<ChatMessageProps[]>([]);
  const messageEndRef = useRef<HTMLDivElement | null>(null);
  const observer = useRef<IntersectionObserver | null>();
  const theme = useTheme();

  useEffect(() => {
    observer.current = new IntersectionObserver(() => {
      if (lastChats.length === chats.length) return;
      if (messageEndRef) messageEndRef.current?.focus();
    });
    if (messageEndRef.current) observer.current.observe(messageEndRef.current);
  }, [messageEndRef, lastChats, chats]);

  useEffect(() => {
    if (lastChats.length === chats.length) return;
    const latestMyMessageIndex = chats.findLastIndex(
      (chat) => chat.isMyMessage,
    );
    if (latestMyMessageIndex >= lastChats.length)
      messageEndRef?.current?.focus();

    setLastChats(chats);
  }, [lastChats, chats]);

  return (
    <div css={list({ theme })}>
      {chats.map((chat) => (
        <Chat key={chat.message + chat.time} {...chat} />
      ))}
      <div ref={messageEndRef}></div>
    </div>
  );
}
