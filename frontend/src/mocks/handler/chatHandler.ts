import {
  BetChatRoomDetail,
  Chat,
  ChatRoomType,
  ChattingPreview,
  MoimChatRoomDetail,
} from '@_types/index';
import { GetChatRoomDetail, GetChattingPreview } from '@_apis/responseTypes';
import { HttpResponse, http } from 'msw';

import mockedChats from './mockedChats';

let nowChatIndex = 0;

// [betChats,moimChats,moimChats,betChats]
export const nowChatServerData: Chat[][] = [mockedChats.slice(), [], [], []];
const chatRoomDetail: (BetChatRoomDetail | MoimChatRoomDetail)[] = [
  {
    chatRoomId: 0,
    attributes: {
      isLoser: false,
      betId: 22,
      loser: {
        nickname: '패배자',
        profile: '',
        role: 'MOIMER',
      },
    },
    type: 'BET',
    title: '내가 안 걸린 모임',
    participations: [
      {
        nickname: '1',
        profile: '나',
        role: 'MOIMER',
      },
    ],
  },
  {
    chatRoomId: 1,
    attributes: {
      place: '모임',
      isMoimer: false,
      isStarted: true,
      description: '',
      date: '',
      time: '12:12:00',
      moimId: 2,
    },
    type: 'MOIM',
    title: '위와 같은 가격',
    participations: [{ nickname: '111', role: 'MOIMEE', profile: '' }],
  },
  {
    chatRoomId: 2,
    attributes: {
      place: '우테코 잠실캠',
      isMoimer: false,
      isStarted: false,
      description: '',
      date: '2020-12-21',
      time: '12:12:00',
      moimId: 2,
    },
    type: 'MOIM',
    title: '위와 같은 모임',
    participations: [
      {
        nickname: '',
        profile: '',
        role: 'MOIMER',
      },
    ],
  },
  {
    chatRoomId: 0,
    attributes: {
      isLoser: true,
      betId: 22,
      loser: {
        nickname: '',
        profile: '',
        role: 'MOIMER',
      },
    },
    type: 'BET',
    title: '내가 걸린 모임',
    participations: [
      {
        nickname: '1',
        profile: '',
        role: 'MOIMER',
      },
    ],
  },
];
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
    `${process.env.API_BASE_URL}/v1/darakbang/:darakbangId/chatroom/:chatRoomId`,
    async ({ request }) => {
      const chatRoomId = +(request.url.split('/').at(-1)?.split('?')[0] || 0);
      const json = (await request.json()) as { content: string };
      nowChatServerData[chatRoomId].push({
        chatId: (nowChatServerData[chatRoomId].at(-1)?.chatId || 0) + 1,
        content: json.content,
        isMyMessage: true,
        participation: { nickname: '내 닉네임', profile: '', role: 'MOIMEE' },
        date: new Date().toISOString().split('T')[0],
        time: new Date().toISOString().split('T')[1].slice(0, 8),
        chatType: 'BASIC',
      });

      return HttpResponse.json({ data: true });
    },
  ),
  http.get(
    `${process.env.API_BASE_URL}/v1/darakbang/*/chatRoom/*/details`,
    async ({ request }) => {
      const chatRoomId = +(request.url.split('/').at(-2) || 1);

      return HttpResponse.json<GetChatRoomDetail>({
        data: chatRoomDetail[chatRoomId],
      });
    },
  ),

  http.get(
    `${process.env.API_BASE_URL}/v1/darakbang/*/chatroom/preview`,
    ({ request }) => {
      const url = new URL(request.url);

      const chatRoomType: ChatRoomType =
        (url.searchParams.get('chatRoomType') as ChatRoomType) || 'MOIM';

      if (chatRoomType === 'BET') {
        return HttpResponse.json<GetChattingPreview>({
          data: {
            previews: [
              {
                chatRoomId: 0,
                title: '내가 안 걸린 모임',
                participations: [
                  { nickname: '111', role: 'MOIMEE', profile: '' },
                ],
                isStarted: true,
                unreadChatCount: 1,
                lastContent: '뭐요',
              },
              {
                chatRoomId: 3,
                title: '내가 걸린 모임',
                participations: [
                  { nickname: '111', role: 'MOIMEE', profile: '' },
                ],
                isStarted: true,
                unreadChatCount: 1,
                lastContent: '뭐요',
              },
            ],
          },
        });
      }

      if (chatRoomType === 'MOIM') {
        return HttpResponse.json<{
          data: { previews: ChattingPreview[] };
        }>({
          data: {
            previews: [
              {
                chatRoomId: 1,
                title: '모임',
                participations: [
                  { nickname: '111', role: 'MOIMEE', profile: '' },
                ],
                isStarted: true,
                unreadChatCount: 0,
                lastContent: nowChatServerData[1].at(-1)?.content || '',
              },
              {
                chatRoomId: 2,
                title: '위와 같은 모임',
                participations: [
                  { nickname: '111', role: 'MOIMEE', profile: '' },
                ],
                isStarted: false,
                unreadChatCount: 0,
                lastContent: nowChatServerData[2].at(-1)?.content || '',
              },
            ],
          },
        });
      }
    },
  ),
  http.get(
    new RegExp(
      `^${process.env.API_BASE_URL}/v1/darakbang/[^/]+/chatroom/[^/]+$`,
    ),
    async ({ request }) => {
      const url = new URL(request.url);

      const chatRoomId = +(request.url.split('/').at(-1)?.split('?')[0] || 0);

      const recentChatId = +(url.searchParams.get('recentChatId') || 0);

      return HttpResponse.json({
        data: {
          chats: nowChatServerData[chatRoomId]?.slice(recentChatId),
        },
      });
    },
  ),
];
