import {
  BetChatRoomDetail,
  Chat,
  ChatRoomDetail,
  ChatRoomType,
  ChattingPreview,
  MoimChatRoomDetail,
  MoimInfo,
  Participation,
  Role,
} from '@_types/index';
import {
  GetBet,
  GetBetDetail,
  GetBets,
  GetChamyoAll,
  GetChamyoMine,
  GetChat,
  GetChatRoomDetail,
  GetChattingPreview,
  GetDarakbangInviteCode,
  GetDarakbangMemberProfile,
  GetDarakbangMembers,
  GetDarakbangMine,
  GetDarakbangNameByCode,
  GetMoim,
  GetMoims,
  GetMyInfo,
  GetMyRoleInDarakbang,
  GetNotifications,
  GetPleases,
  GetZzimMine,
} from './responseTypes';

import ApiClient from './apiClient';
import { ApiError } from '@_utils/customError/ApiError';
import { Filter } from '@_pages/Moim/MainPage/components/HomeMainContent/MyMoim/MyMoimListFilters/MyMoimListFilters';

export const getMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithLastDarakbangId('/moim');

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMyFilteredMoims = async (
  filter: Filter['api'],
): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/moim/mine?filter=${filter}`,
  );

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMyZzimMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithLastDarakbangId('/moim/zzim');

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getMoim = async (moimId: number): Promise<MoimInfo> => {
  const response = await ApiClient.getWithLastDarakbangId(`/moim/${moimId}`);

  const json: GetMoim = await response.json();
  return json.data;
};

export const getChatPreview = async (
  chatRoomType: ChatRoomType,
): Promise<ChattingPreview[]> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/chatroom/preview?chatRoomType=${chatRoomType}`,
  );

  const json: GetChattingPreview = await response.json();
  return json.data.previews;
};

export const getChatRoomDetail = async (
  chatRoomId: number,
): Promise<BetChatRoomDetail | MoimChatRoomDetail | ChatRoomDetail> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/chatroom/${chatRoomId}/details`,
  );

  const json: GetChatRoomDetail = await response.json();
  const chatRoomDetail = json.data;
  if (chatRoomDetail.type === 'BET') return chatRoomDetail as BetChatRoomDetail;
  if (chatRoomDetail.type === 'MOIM')
    return chatRoomDetail as MoimChatRoomDetail;
  return chatRoomDetail;
};

export const getChat = async (
  chatRoomId: number,
  recentChatId?: number,
): Promise<Chat[]> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/chatroom/${chatRoomId}?recentChatId=${recentChatId || 0}`,
  );

  const json: GetChat = await response.json();
  return json.data.chats;
};

export const getMyMoims = async (): Promise<MoimInfo[]> => {
  const response = await ApiClient.getWithLastDarakbangId(`/moim/mine`);

  const json: GetMoims = await response.json();
  return json.data.moims;
};

export const getChamyoMine = async (moimId: number): Promise<Role> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/chamyo/mine?moimId=${moimId}`,
  );

  const json: GetChamyoMine = await response.json();
  return json.data.role;
};

export const getZzimMine = async (moimId: number): Promise<boolean> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/zzim/mine?moimId=${moimId}`,
  );

  const json: GetZzimMine = await response.json();
  return json.data.isZzimed;
};

export const getChamyoAll = async (
  moimId: number,
): Promise<Participation[]> => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/chamyo/all?moimId=${moimId}`,
  );

  const json: GetChamyoAll = await response.json();
  return json.data.chamyos;
};

export const getPleases = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/please');

  const json: GetPleases = await response.json();
  return json.data.pleases;
};

export const getMyInfo = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/member/mine');

  const json: GetMyInfo = await response.json();
  return json.data;
};

export const getNotifications = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/notification/mine');

  const json: GetNotifications = await response.json();
  return json.data.notifications;
};

export const getMyDarakbangs = async () => {
  const response = await ApiClient.getWithAuth('/darakbang/mine');

  const json: GetDarakbangMine = await response.json();
  return json.data.darakbangResponses;
};

export const getMyRoleInDarakbang = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/role');

  const json: GetMyRoleInDarakbang = await response.json();
  return json.data.role;
};

export const getDarakbangMembers = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/members');

  const json: GetDarakbangMembers = await response.json();
  return json.data.responses;
};

export const getDarakbangInviteCode = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/code');

  const json: GetDarakbangInviteCode = await response.json();
  return json.data.code;
};

export const getDarakbangNameByCode = async (code: string) => {
  const response = await ApiClient.getWithAuth(
    '/darakbang/validation?code=' + code,
  ).catch((e) => {
    if (e instanceof ApiError && e.status === 401) {
      throw e;
    }
    return {
      json: () => {
        return { data: { name: '' } };
      },
    };
  });

  const json: GetDarakbangNameByCode = await response.json();
  return json.data.name;
};

export const getDarakbangNameById = async () => {
  const response = await ApiClient.getWithLastDarakbangId('');

  const json: GetDarakbangNameByCode = await response.json();
  return json.data.name;
};

export const getBets = async () => {
  const response = await ApiClient.getWithLastDarakbangId('/bet');

  const json: GetBets = await response.json();
  return json.data.bets;
};

export const getBet = async (betId: number) => {
  const response = await ApiClient.getWithLastDarakbangId(`/bet/${betId}`);

  const json: GetBet = await response.json();
  return json.data;
};

export const getBetResult = async (betId: number) => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/bet/${betId}/result`,
  );

  const json: GetBetDetail = await response.json();
  return json.data.nickname;
};

export const getDarakbangMemberProfile = async (darakbangMemberId: number) => {
  const response = await ApiClient.getWithLastDarakbangId(
    `/members/${darakbangMemberId}/profile`,
  );

  const json: GetDarakbangMemberProfile = await response.json();
  return json.data;
};
