import { Chat, MoimInfo, Participation } from '@_types/index';

import {
  GetChamyoAll,
  GetChamyoMine,
  GetChat,
  GetMoim,
  GetMoims,
  GetMyInfo,
  GetZzimMine,
} from './responseTypes';

import ApiClient from './apiClient';
import { checkStatus } from './apiconfig';
import { Filter } from '@_components/MyMoimListFilters/MyMoimListFilters';

export const getMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithAuth('moim');
  checkStatus(response);

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMyFilteredMoims = async (
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

export const getChat = async (
  moimId: number,
  recentChatId?: number,
): Promise<Chat[]> => {
  const response = await ApiClient.getWithAuth(
    `chat?moimId=${moimId}&recentChatId=${recentChatId || 0}`,
  );
  checkStatus(response);

  const json: GetChat = await response.json();
  return json.data.chats;
};

export const getMyMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithAuth(`moim/mine`);
  checkStatus(response);

  const json: GetMoims = await response.json();
  return json.data.moims;
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
  const response = await ApiClient.getWithAuth(`chamyo/all?moimId=${moimId}`);
  checkStatus(response);

  const json: GetChamyoAll = await response.json();
  return json.data.chamyos;
};

export const getMyInfo = async () => {
  const response = await ApiClient.getWithAuth('member/mine');
  checkStatus(response);

  const json: GetMyInfo = await response.json();
  return json.data;
};
