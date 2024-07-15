import { GetMoim, MoimInfo } from '../types/requests';

import ENV from './env';

export const getMoims = async (): Promise<MoimInfo[]> => {
  const url = `${ENV.baseUrl}/${'v1/moim'}`;

  const headers = new Headers();
  headers.append('Content-Type', 'application/json');
  // headers.append('a', 'b');

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      Accept: '*/*',
      'Accept-Encoding': 'gzip, deflate',
      'Accept-Language': 'ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7',
      'Cache-Control': 'no-cache',
      Connection: 'keep-alive',
      Host: '43.202.67.25',
      Origin: 'http://localhost:8080',
      Pragma: 'no-cache',
      Referer: 'http://localhost:8080/',
      'User-Agent':
        'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36',
    },
  };

  const response = await fetch(url, options);

  const statusHead = Math.floor(response.status / 100);
  if (statusHead === 4 || statusHead === 5)
    throw new Error('모임을 받아오지 못했습니다.');
  const json = (await response.json()) as GetMoim;
  return json.data;
};
