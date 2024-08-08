import ApiClient from './apiClient';
import { MoimInputInfo } from '@_types/index';

export const patchCompleteMoim = async (moimId: number) => {
  await ApiClient.patchWithAuth(`moim/${moimId}/complete`, {
    moimId,
  });
};

export const patchCancelMoim = async (moimId: number) => {
  await ApiClient.patchWithAuth(`moim/${moimId}/cancel`, {
    moimId,
  });
};

export const patchModifyMoim = async (moimId: number, state: MoimInputInfo) => {
  await ApiClient.patchWithAuth(`moim`, {
    moimId,
    ...state,
  });
};

export const patchReopenMoim = async (moimId: number) => {
  await ApiClient.patchWithAuth(`moim/${moimId}/reopen`, {
    moimId,
  });
};

export const patchOpenChat = async (moimId: number) => {
  await ApiClient.patchWithAuth(`chat/open?moimId=${moimId}`);
};
