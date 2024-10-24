import { API_URL } from '@_apis/endPoints';
import { http, HttpResponse } from 'msw';

export const profileHandler = [
  http.get(`${API_URL.profile(1)}`, () => {
    return HttpResponse.json({
      data: {
        darakbangMemberId: 1,
        name: '김다락방',
        nickname: '다락방',
        url: 'https://darakbang.com',
        description:
          '다락방입니다. 공백 열두글자 뽀로로로의 한줄소개입니다 길어지면 밑으로 내려갸야해요. 공백 열두글자 뽀로로로의 한줄소개입니다 길어지면 밑으로 내려갸야해요.',
      },
    });
  }),
];
