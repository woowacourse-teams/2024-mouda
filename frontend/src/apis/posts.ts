import { MoimInfo, PostMoim } from '../types/requests';

import ENV from './env';

export const postMoim = async (moim: MoimInfo): Promise<number> => {
  const url = `${ENV.baseUrl}/${'v1/moim'}`;

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      body: JSON.stringify(moim),
    },
  };

  const response = await fetch(url, options);

  const statusHead = Math.floor(response.status / 100);
  if (statusHead === 4 || statusHead === 5)
    throw new Error('모임을 업데이트하지 못했습니다.');

  const json = (await response.json()) as PostMoim;
  return json.id;
};
