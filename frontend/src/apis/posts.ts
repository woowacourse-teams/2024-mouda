import { checkStatus, defaultOptions } from './apiconfig';

import ENDPOINTS from '@_apis/endPoints';
import { MoimInputInfo } from '@_types/index';
import { PostMoim } from '@_apis/responseTypes';

const defaultPostOptions = {
  method: 'POST',
  ...defaultOptions,
};
export const postMoim = async (moim: MoimInputInfo): Promise<number> => {
  const url = ENDPOINTS.moim;

  const options = {
    ...defaultPostOptions,
    body: JSON.stringify(moim),
  };

  const response = await fetch(url, options);

  checkStatus(response);

  const json = (await response.json()) as PostMoim;
  return json.data;
};

export const postJoinMoim = async (moimId: number, nickName: string) => {
  const url = `${ENDPOINTS.moims}/join`;
  const options = {
    ...defaultPostOptions,
    body: JSON.stringify({ moimId, nickName }),
  };

  const response = await fetch(url, options);

  await checkStatus(response);
};
