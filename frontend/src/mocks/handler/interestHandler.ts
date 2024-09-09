import { API_URL } from '@_apis/endPoints';
import { http, HttpResponse } from 'msw';
import { updatePlease } from './mockPleases';

interface InterestRequestBody {
  pleaseId: number;
  isInterested: boolean;
}

export const interestHandler = [
  http.post(`${API_URL.interest}`, async ({ request }) => {
    const { pleaseId, isInterested } =
      (await request.json()) as InterestRequestBody;
    updatePlease(pleaseId, isInterested);
    return HttpResponse.json({ success: true });
  }),
];
