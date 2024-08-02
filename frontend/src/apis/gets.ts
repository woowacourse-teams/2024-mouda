import { MoimInfo } from '@_types/index';
import ApiClient from './apiClient';
import { GetMoim, GetMoims } from './responseTypes';
import { checkStatus } from './apiconfig';
import { Filter } from '@_components/MyMoimListFilters/MyMoimListFilters';

export const getMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithAuth('moim');
  checkStatus(response);

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMyMoims = async (
  filter: Filter['api'],
): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithAuth(`moim/mine?filter=${filter}`);
  checkStatus(response);

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMyZzimMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithAuth('moim/zzim');
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
