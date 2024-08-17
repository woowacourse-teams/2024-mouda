import { HttpResponse, http } from 'msw';

import ENDPOINTS from '@_apis/endPoints';

export const notificationHandler = [
  http.get(`${ENDPOINTS.notification}/mine`, () => {
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
