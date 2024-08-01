import { MoimInputInfo } from '@_types/index';
import ApiClient from './apiClient';
import { PostMoim } from './responseTypes';
import { checkStatus } from './apiconfig';

export const postMoim = async (moim: MoimInputInfo): Promise<number> => {
  const response = await ApiClient.postWithAuth('moim', moim);

  checkStatus(response);

  const json: PostMoim = await response.json();
  return json.data;
};

export const postJoinMoim = async (moimId: number, nickname: string) => {
  const response = await ApiClient.postWithAuth('moim/join', {
    moimId,
    nickname,
  });
  await checkStatus(response);
};
