import { API_URL } from '@_apis/endPoints';
import { HttpResponse, http } from 'msw';

// 더미 데이터 생성
const dummyBets = [
  {
    id: 1,
    title: '첫 번째 배팅',
    currentParticipants: 10,
    deadline: '2024-09-26 12:30',
    isAnnounced: false,
  },
  {
    id: 2,
    title: '두 번째 배팅',
    currentParticipants: 5,
    deadline: '2024-09-26 13:00',
    isAnnounced: true,
  },
];

const dummyBetDetail = {
  title: '상세 배팅',
  currentParticipants: 10,
  deadline: '2024-09-26 12:30',
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
  http.get(API_URL.bet.detail(1), () => {
    return HttpResponse.json(dummyBetDetail);
  }),

  // 3. 배팅 생성 API
  http.post(API_URL.bet.create, async ({ request }) => {
    await request.json();
    return HttpResponse.json({
      betId: 3,
      message: '배팅이 생성되었습니다.',
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
