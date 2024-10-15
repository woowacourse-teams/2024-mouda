import { API_URL } from '@_apis/endPoints';
import { HttpResponse, http } from 'msw';

export const myInfoHandler = [
  http.get(`${API_URL.myInfo}`, () => {
    return HttpResponse.json({
      data: {
        nickname: '치코',
        name: '이재민',
        profile: 'sdfd',
        description: '나다',
      },
    });
  }),
];
