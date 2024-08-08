import { HttpResponse, http } from 'msw';

import { Chat } from '@_types/index';
import mockedChats from './mockedChats';

let nowChatIndex = 0;

export const nowChatServerData: Chat[] = [];
export const initChatIndex = () => (nowChatIndex = 0);

export const pushNextChatsIntoSever = () => {
  nowChatServerData.push(
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
  http.get(`http://43.202.67.25/v1/chat`, async ({ request }) => {
    const url = new URL(request.url);

    const recentChatId = +(url.searchParams.get('recentChatId') || 0);

    return HttpResponse.json({
      data: { chats: nowChatServerData.slice(recentChatId) },
    });
  }),
];
