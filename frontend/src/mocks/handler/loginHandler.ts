import { API_URL } from '@_apis/endPoints';
import { HttpResponse, http } from 'msw';

export const notificationHandler = [
  http.get(`${API_URL.notification}/mine`, () => {
    return HttpResponse.json({
      data: {
        notifications: [
          {
            type: 'MOIM_CREATED',
            createdAt: '1시간전',
            message: '테스트',
          },
        ],
      },
    });
  }),
];
