// ./src/apis/gets.ts
import { MoimInfo } from '@_types/index';
import ApiClient from './apiClient';
import { GetMoim, GetMoims } from './responseTypes';
import { checkStatus } from './apiconfig';

export const getMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithAuth('moim');
  checkStatus(response);

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMoim = async (moimId: number): Promise<MoimInfo> => {
  const response = await ApiClient.getWithAuth(`moim/${moimId}`);
  checkStatus(response);

  const json: GetMoim = await response.json();
  return json.data;
};
