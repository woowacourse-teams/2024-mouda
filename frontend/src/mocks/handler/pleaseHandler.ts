import { API_URL } from '@_apis/endPoints';
import { http, HttpResponse } from 'msw';
import { pleasesData } from './mockPleases';

export const pleaseHandler = [
  http.get(`${API_URL.please}`, () => {
    return HttpResponse.json({
      data: {
        pleases: pleasesData,
      },
    });
  }),
];
