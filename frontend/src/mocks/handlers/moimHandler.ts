import { http, HttpResponse } from 'msw';
import ENDPOINTS from '@_apis/endPoints';

export const moimHandler = [
  http.get(`${ENDPOINTS.moims}`, () => {
    return HttpResponse.json({
      data: {
        moims: [
          {
            moimId: 1,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
          },
        ],
      },
    });
  }),
];
