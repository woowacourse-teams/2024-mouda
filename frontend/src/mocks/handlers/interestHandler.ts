import ENDPOINTS from '@_apis/endPoints';
import { http, HttpResponse } from 'msw';
import { updatePlease } from './mockPleases';

interface InterestRequestBody {
  pleaseId: number;
  interesting: boolean;
}

export const interestHandler = [
  http.post(`${ENDPOINTS.interest}`, async ({ request }) => {
    const { pleaseId, interesting } =
      (await request.json()) as InterestRequestBody;
    updatePlease(pleaseId, interesting);
    return HttpResponse.json({ success: true });
  }),
];
