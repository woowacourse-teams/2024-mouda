import ENDPOINTS from '@_apis/endPoints';
import { http, HttpResponse } from 'msw';
import { pleasesData } from './mockPleases';

export const pleaseHandler = [
  http.get(`${ENDPOINTS.please}`, () => {
    return HttpResponse.json({
      data: {
        pleases: pleasesData,
      },
    });
  }),
];
