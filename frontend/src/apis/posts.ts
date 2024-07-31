import { MoimInputInfo } from '@_types/index';
import ApiClient from './apiClient';

export const postMoim = async (moim: MoimInputInfo): Promise<number> => {
  const json = await ApiClient.postWithAuth('moim', moim);
  return json.data;
};

export const postJoinMoim = async (moimId: number, nickname: string) => {
  await ApiClient.postWithAuth('moim/join', { moimId, nickname });
};
