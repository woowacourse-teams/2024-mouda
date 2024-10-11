import { Chat, ChatRoomType } from '@_types/index';
import { HttpResponse, http } from 'msw';

import mockedChats from './mockedChats';

let nowChatIndex = 0;

// [betChats,moimChats]
export const nowChatServerData: Chat[][] = [mockedChats.slice(), []];
export const initChatIndex = () => (nowChatIndex = 0);

export const pushNextChatsIntoSever = () => {
  nowChatServerData[1].push(
    ...mockedChats.slice(...chatSliceIndexes[nowChatIndex++]),
  );
};

export const chatSliceIndexes = [
  [0, 0],
  [0, 2],
  [2, 2],
  [2, 8],
  [8, 10],
  [10, 13],
  [13, mockedChats.length],
];
export const chatHandler = [
  http.post(
    `${process.env.API_BASE_URL}/v1/darakbang/*/chatroom`,
    async ({ request }) => {
      const chatRoomId = +(request.url.split('/').at(-1) || 0);
      const json = (await request.json()) as { content: string };
      nowChatServerData[chatRoomId].push({
        chatId: nowChatServerData[chatRoomId].length,
        content: json.content,
        isMyMessage: true,
        nickname: '내 닉네임',
        date: new Date().toISOString().split('T')[0],
        time: new Date().toISOString().split('T')[1].slice(0, 8),
        chatType: 'BASIC',
      });
    },
  ),

  http.get(
    `${process.env.API_BASE_URL}/v1/darakbang/*/chat`,
    async ({ request }) => {
      const url = new URL(request.url);

      const chatRoomId = +(request.url.split('/').at(-1) || 1);

      const recentChatId = +(url.searchParams.get('recentChatId') || 0);
      return HttpResponse.json({
        data: {
          chats: nowChatServerData[chatRoomId]?.slice(recentChatId + 1),
        },
      });
    },
  ),

  http.get(
    `${process.env.API_BASE_URL}/v1/darakbang/*/chat/preview`,
    ({ request }) => {
      const url = new URL(request.url);

      const chatRoomType: ChatRoomType =
        (url.searchParams.get('chatRoomType') as ChatRoomType) || 'MOIM';

      if (chatRoomType === 'BET') {
        return HttpResponse.json({
          data: {
            chatPreviewResponses: [
              {
                chatRoomId: 0,
                title: '베팅',
                currentPeople: 3,
                isStarted: true,
                lastContent: 'aas',
                lastReadChatId: 0,
              },
            ],
          },
        });
      }

      if (chatRoomType === 'MOIM') {
        return HttpResponse.json({
          data: {
            chatPreviewResponses: [
              {
                chatRoomId: 1,
                title: '모임',
                currentPeople: 3,
                isStarted: true,
                lastContent: 'aas',
                lastReadChatId: 0,
              },
            ],
          },
        });
      }
    },
  ),
];
