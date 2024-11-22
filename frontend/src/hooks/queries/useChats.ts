import { useRef, useState } from 'react';

import { Chat } from '@_types/index';
import QUERY_KEYS from '@_constants/queryKeys';
import { getChat } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useChats(chatRoomId: number) {
  const [chats, setChats] = useState<Chat[]>([]);
  const lastChatIdRef = useRef(0);
  const { isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.chat,
      chatRoomId,
    ],
    queryFn: async () => {
      const newChats = await getChat(chatRoomId, lastChatIdRef.current);
      if (!newChats) return [];
      if (newChats.length === 0) return [];
      if (lastChatIdRef.current === newChats.at(-1)?.chatId) return [];

      setChats([...chats, ...newChats]);
      // @ts-expect-error:newChat.at(-1)은 항상 존재
      lastChatIdRef.current = newChats.at(-1)?.chatId;
      return chats;
    },
    refetchInterval: 100,
  });

  return { chats, isLoading };
}
