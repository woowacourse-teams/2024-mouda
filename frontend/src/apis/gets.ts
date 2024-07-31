// ./src/apis/gets.ts
import { MoimInfo } from '@_types/index';
import ApiClient from './apiClient';
import { GetMoim, GetMoims } from './responseTypes';

export const getMoims = async (): Promise<MoimInfo[]> => {
  const json: GetMoims = await ApiClient.getWithAuth('moim');
  return json.data.moims;
};
export const getMoim = async (moimId: number): Promise<MoimInfo> => {
  const json: GetMoim = await ApiClient.getWithAuth(`moim/${moimId}`);
  return json.data;
};
