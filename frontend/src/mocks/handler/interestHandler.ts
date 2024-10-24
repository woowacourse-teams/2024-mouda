import ENDPOINTS from '@_apis/endPoints';
import { http, HttpResponse } from 'msw';
import { updatePlease } from './mockPleases';

interface InterestRequestBody {
  pleaseId: number;
  isInterested: boolean;
}

export const interestHandler = [
  http.post(`${ENDPOINTS.interest}`, async ({ request }) => {
    const { pleaseId, isInterested } =
      (await request.json()) as InterestRequestBody;
    updatePlease(pleaseId, isInterested);
    return HttpResponse.json({ success: true });
  }),
];
