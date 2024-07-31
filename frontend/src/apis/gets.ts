import ENDPOINTS from '@_apis/endPoints';
import { GetMoim, GetMoims } from '@_apis/responseTypes';
import { MoimInfo } from '@_types/index';
import { checkStatus, defaultOptions } from './apiconfig';

const defaultGetOptions = {
  method: 'GET',
  ...defaultOptions,
};
export const getMoims = async (): Promise<MoimInfo[]> => {
  const url = ENDPOINTS.moims;

  const response = await fetch(url, defaultGetOptions);

  checkStatus(response);
  const json = (await response.json()) as GetMoims;
  return json.data.moims;
};

export const getMoim = async (moimId: number): Promise<MoimInfo> => {
  const url = `${ENDPOINTS.moim}/${moimId}`;

  const response = await fetch(url, defaultGetOptions);

  checkStatus(response);

  const json = (await response.json()) as GetMoim;
  return json.data;
};
