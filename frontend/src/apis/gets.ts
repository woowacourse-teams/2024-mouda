// ./src/apis/gets.ts
import { MoimInfo, Participation } from '@_types/index';
import ApiClient from './apiClient';
import {
  GetChamyoAll,
  GetChamyoMine,
  GetMoim,
  GetMoims,
  GetZzimMine,
} from './responseTypes';
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

export const getChamyoMine = async (
  moimId: number,
): Promise<'MOIMER' | 'MOIMEE' | 'NON_MOIMEE'> => {
  const response = await ApiClient.getWithAuth(`chamyo/mine?moimId=${moimId}`);
  checkStatus(response);

  const json: GetChamyoMine = await response.json();
  return json.data.role;
};

export const getZzimMine = async (moimId: number): Promise<boolean> => {
  const response = await ApiClient.getWithAuth(`zzim/mine?moimId=${moimId}`);
  checkStatus(response);

  const json: GetZzimMine = await response.json();
  return json.data.isZzimed;
};

export const getChamyoAll = async (
  moimId: number,
): Promise<Participation[]> => {
  const response = await ApiClient.getWithAuth(`chamyo/mine?moimId=${moimId}`);
  checkStatus(response);

  const json: GetChamyoAll = await response.json();
  return json.data.chamoys;
};
