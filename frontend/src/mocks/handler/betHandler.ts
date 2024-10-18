import { GetBet, PostBet } from '@_apis/responseTypes';
import { HttpResponse, http } from 'msw';

import { API_URL } from '@_apis/endPoints';
import { BetDetail } from '@_types/index';

const BET_ID = 10;
// 더미 데이터 생성
const dummyBets = [
  {
    id: BET_ID,
    title: '첫 번째 배팅',
    currentParticipants: 10,
    deadline: '2024-09-26T12:30',
    isAnnounced: false,
  },
  {
    id: BET_ID,
    title: '두 번째 배팅',
    currentParticipants: 5,
    deadline: '2024-10-26T13:00',
    isAnnounced: false,
  },
];

const dummyBetDetail: BetDetail = {
  title: '상세 배팅',
  currentParticipants: 10,
  deadline:
    '2024-09-26T12:30' as `${number}-${number}-${number}T${number}:${number}:${number}`,
  isAnnounced: false,
  participants: [
    {
      nickname: '사용자1',
      id: 101,
      profileUrl: 'https://example.com/profile1.jpg',
    },
    {
      nickname: '사용자2',
      id: 102,
      profileUrl: 'https://example.com/profile2.jpg',
    },
  ],
  myRole: 'MOIMEE',
  chatroomId: null,
};

export const betHandler = [
  // 1. 배팅 전체 조회 API
  http.get(API_URL.bet.all, () => {
    return HttpResponse.json({
      data: { bets: dummyBets },
    });
  }),

  // 2. 배팅 상세 조회 API
  http.get(API_URL.bet.detail(BET_ID), () => {
    return HttpResponse.json<GetBet>({ data: dummyBetDetail });
  }),

  // 3. 배팅 생성 API
  http.post(API_URL.bet.create, async ({ request }) => {
    await request.json();
    return HttpResponse.json<PostBet>({
      data: {
        betId: BET_ID,
      },
    });
  }),

  // 4. 배팅 참여 API
  http.post(API_URL.bet.participate(1), async ({ request }) => {
    await request.json();
    return HttpResponse.json({
      message: '배팅에 참여하였습니다.',
    });
  }),

  // 5. 배팅 결과 조회 API
  http.get(API_URL.bet.result(1), () => {
    return HttpResponse.json({
      nickname: '우승자 닉네임',
    });
  }),
];
