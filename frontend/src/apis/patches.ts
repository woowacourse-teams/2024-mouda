import ApiClient from './apiClient';
import { MoimInputInfo } from '@_types/index';
import { PostMoimBody } from './responseTypes';

export const patchCompleteMoim = async (moimId: number) => {
  await ApiClient.patchWithLastDarakbangId(`/moim/${moimId}/complete`, {
    moimId,
  });
};

export const patchCancelMoim = async (moimId: number) => {
  await ApiClient.patchWithLastDarakbangId(`/moim/${moimId}/cancel`, {
    moimId,
  });
};

export const patchModifyMoim = async (moimId: number, moim: MoimInputInfo) => {
  const parsedMoim: PostMoimBody = {
    ...moim,
    date: moim.date || undefined,
    time: moim.time || undefined,
    place: moim.place || undefined,
  };

  await ApiClient.patchWithLastDarakbangId(`/moim`, {
    moimId,
    ...parsedMoim,
  });
};

export const patchReopenMoim = async (moimId: number) => {
  await ApiClient.patchWithLastDarakbangId(`/moim/${moimId}/reopen`, {
    moimId,
  });
};

export const patchOpenChat = async (moimId: number) => {
  const response = await ApiClient.patchWithLastDarakbangId(
    `/chatroom/open?moimId=${moimId}`,
  );
  const json = await response.json();
  return json.data;
};
