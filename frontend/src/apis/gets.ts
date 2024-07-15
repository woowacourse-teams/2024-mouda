import { GetMoim, Moim } from '../types/requests';

export const getMoims = async (): Promise<Moim[]> => {
  const url = `${'/v1/moim'}/${'v1/moim'}`;

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(url, options);

  const statusHead = Math.floor(response.status / 100);
  if (statusHead === 4 || statusHead === 5)
    throw new Error('모임을 받아오지 못했습니다.');
  const json = (await response.json()) as GetMoim;
  return json.data;
};
